package com.productList;

import com.common.Loader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RestOperations restTemplate ;


    public ProductListForCategory CallProductListByCategoryService(String url, String category, int page) throws HttpMessageNotReadableException{
        ProductListForCategory list = restTemplate.getForObject(url + "?api_key="+API_KEY+"&sub_category=" + category + "&page=" + page, ProductListForCategory.class);
        return list;
    }
    public void load(Object category) {
        String categoryName = (String)category;
        ProductListForCategory productList = null;
        try {
            int page = 1;
            while(true){
                productList = CallProductListByCategoryService(url_product_list_by_category,categoryName, page);
                handelProductList(productList.getData());
                page ++;
            }
        } catch (HttpMessageNotReadableException ee){
            log.error("<<<<<<<< No records found or end of records for category" + categoryName+" moving ahead >>>>>>>>>");

        }

    }

    private void handelProductList(List<ProductList> list){
        for (ProductList item : list){
            loadProductByProductId(item.getProduct_id());
           // loadProductSpecsByProductId(item.getProduct_id());
        }
    }
    private void loadProductSpecsByProductId(String product_id) {
    }

    private void loadProductByProductId(String product_id) {
        ProductDetails detail =  CallProductByIdService(url_product_details_by_id, product_id);

    }

    public ProductDetails CallProductByIdService(String url, String id) throws HttpMessageNotReadableException{
        log.info(url + "?api_key="+API_KEY+"&id=" + id );
        ProductDetails detail = restTemplate.getForObject(url + "?api_key="+API_KEY+"&id=" + id , ProductDetails.class);
        return detail;
    }
}
