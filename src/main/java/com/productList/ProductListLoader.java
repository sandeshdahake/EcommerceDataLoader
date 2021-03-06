package com.productList;

import com.categoryList.CategoryRepository;
import com.common.ApiHitCounterService;
import com.common.Loader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.filters.FilterInfo;
import com.filters.FilterRepository;
import org.apache.commons.lang.math.NumberUtils;
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

    @Value("${load.product.count}")
    private String maxProductCount;

    @Autowired
    ApiHitCounterService apiHitCounterService;

    List<String> productStringList;
    public ProductListForCategory CallProductListByCategoryService(String url, String category, int page, boolean isFashion) throws HttpMessageNotReadableException{
        String apiUrl = null;
        if(isFashion){
            apiUrl = url + "?api_key="+API_KEY+"&sub_category=" + category + "&sort=popularity&page=" + page + "&can_compare=0";
        }else{
            apiUrl = url + "?api_key="+API_KEY+"&sub_category=" + category + "&sort=popularity&page=" + page + "&can_compare=1";

        }
        log.info(apiUrl);
        ProductListForCategory list = restTemplate.getForObject(apiUrl, ProductListForCategory.class);
        apiHitCounterService.incrementCounter();
        return list;
    }
    public void load(Object category, boolean isFashion) {
        String categoryName = (String)category;
        ProductListForCategory productList = null;
        int totalProductsLoaded = 0 ;
        productStringList = productRepository.getProductNames();
        try {
            int page = 1;
            int count = 100 ;
            if(NumberUtils.isNumber(maxProductCount)){
                count = Integer.valueOf(maxProductCount);
            }

            while(true && totalProductsLoaded <= count){
                productList = CallProductListByCategoryService(url_product_list_by_category,categoryName, page, isFashion);
                handelProductList(productList.getData());
                totalProductsLoaded = totalProductsLoaded + productList.getData().size();
                page ++;
            }
        } catch (HttpMessageNotReadableException ee){
            log.error("<<<<<<<< No records found or end of records for category" + categoryName+" moving ahead >>>>>>>>>");
            log.info(ee.getMessage());
        }
        categoryRepository.markCategoryProcessed(categoryName);

    }

    private void handelProductList(List<ProductList> list){
        for (ProductList item : list){
            if(!productStringList.contains(item.getProduct_id())){
                loadProductByProductId(item.getProduct_id());
            }
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
        apiHitCounterService.incrementCounter();
        return detail;
    }
    private void loadProductSpecsByProductId(String product_id)  {
        ProductSpecs specs = CallProductSpecsByIdService(url_product_specs_by_id,product_id);
        productRepository.persistProductSpecs(specs, product_id);
    }

    public ProductSpecs CallProductSpecsByIdService(String url, String id) throws HttpMessageNotReadableException{
        log.info(url + "?api_key="+API_KEY+"&id=" + id );
        ProductSpecs specs = restTemplate.getForObject(url + "?api_key="+API_KEY+"&id=" + id , ProductSpecs.class);
        apiHitCounterService.incrementCounter();;
        return specs;
    }

    public void loadProductFiltersByCateory(Object category){
        String categoryName = (String)category;
        FilterInfo filterInfo = CallFilterListByCategoryService(url_filter_by_id,categoryName);
        filterRepository.persistFilter(filterInfo);

    }

    public FilterInfo CallFilterListByCategoryService(String url, String category) throws HttpMessageNotReadableException{
        FilterInfo filterInfo = restTemplate.getForObject(url + "?api_key="+API_KEY+"&sub_category=" + category , FilterInfo.class);
        apiHitCounterService.incrementCounter();
        return filterInfo;
    }

    public void getMissingSpecification() {
        List<String> listOfProducts =  productRepository.getProductsMissingSpecification();

        for(String productId:listOfProducts){
            loadProductSpecsByProductId(productId);
        }

    }

    @Override
    public void load(Object object) {

    }
}
