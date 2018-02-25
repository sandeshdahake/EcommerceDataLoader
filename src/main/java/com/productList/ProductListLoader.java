package com.productList;

import com.categoryList.CategoryRepository;
import com.common.Loader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.filters.FilterInfo;
import com.filters.FilterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

@Controller
public class ProductListLoader implements Loader {
    Set<String> product_id_set;
    private static final Logger log = LoggerFactory.getLogger(ProductListLoader.class);
    final String url_product_list_by_category ="https://price-api.datayuge.com/api/v1/compare/list";
    final String url_product_details_by_id ="https://price-api.datayuge.com/api/v1/compare/detail";
    final String url_product_specs_by_id ="https://price-api.datayuge.com/api/v1/compare/specs";
    final String url_filter_by_id ="https://price-api.datayuge.com/api/v1/compare/list/filters";

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RestOperations restTemplate ;
    @Autowired
    ProductRepository productRepository;

    @Autowired
    FilterRepository filterRepository;

    @Value("${category.max.product.count}")
    private int maxProductCount;

    public ProductListForCategory CallProductListByCategoryService(String url, String category, int page) throws HttpMessageNotReadableException{
        ProductListForCategory list = restTemplate.getForObject(url + "?api_key="+API_KEY+"&sub_category=" + category + "&sort=popularity&page=" + page, ProductListForCategory.class);
        return list;
    }
    public void load(Object category) {
        String categoryName = (String)category;
        ProductListForCategory productList = null;
        int totalProductsLoaded = 0 ;
        try {
            int page = 1;
            while(true && totalProductsLoaded <= maxProductCount){
                productList = CallProductListByCategoryService(url_product_list_by_category,categoryName, page);
                handelProductList(productList.getData());
                totalProductsLoaded = totalProductsLoaded + productList.getData().size();
                page ++;
            }
        } catch (HttpMessageNotReadableException ee){
            log.error("<<<<<<<< No records found or end of records for category" + categoryName+" moving ahead >>>>>>>>>");

        }
        categoryRepository.markCategoryProcessed(categoryName);

    }

    private void handelProductList(List<ProductList> list){
        for (ProductList item : list){
            loadProductByProductId(item.getProduct_id());
            try {
                Thread.sleep(WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("########### Error while waiting : Thread interuppted");
            }
            loadProductSpecsByProductId(item.getProduct_id());
        }
    }

    private void loadProductByProductId(String product_id) {
        ProductDetails detail =  CallProductByIdService(url_product_details_by_id, product_id);
        productRepository.persistProduct(detail);
    }

    public ProductDetails CallProductByIdService(String url, String id) throws HttpMessageNotReadableException{
        log.info(url + "?api_key="+API_KEY+"&id=" + id );
        ProductDetails detail = restTemplate.getForObject(url + "?api_key="+API_KEY+"&id=" + id , ProductDetails.class);
        return detail;
    }
    private void loadProductSpecsByProductId(String product_id) {
        ProductSpecs specs = CallProductSpecsByIdService(url_product_specs_by_id,product_id);
        productRepository.persistProductSpecs(specs, product_id);
    }

    public ProductSpecs CallProductSpecsByIdService(String url, String id) throws HttpMessageNotReadableException{
        log.info(url + "?api_key="+API_KEY+"&id=" + id );
        ProductSpecs specs = restTemplate.getForObject(url + "?api_key="+API_KEY+"&id=" + id , ProductSpecs.class);
        return specs;
    }

    public void loadProductFiltersByCateory(Object category){
        String categoryName = (String)category;
        FilterInfo filterInfo = CallFilterListByCategoryService(url_filter_by_id,categoryName);
        filterRepository.persistFilter(filterInfo);

    }

    public FilterInfo CallFilterListByCategoryService(String url, String category) throws HttpMessageNotReadableException{
        FilterInfo filterInfo = restTemplate.getForObject(url + "?api_key="+API_KEY+"&sub_category=" + category , FilterInfo.class);
        return filterInfo;
    }
}
