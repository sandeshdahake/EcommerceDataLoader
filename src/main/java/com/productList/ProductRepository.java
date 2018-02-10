package com.productList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class ProductRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Transactional
    public void persistProduct(ProductDetails details){
        saveProduct(details.getData());
        saveProductColor(details.getData());
        saveProductImage(details.getData());
        saveStore(details.getData());
    }

    final String SAVE_PRODUCT = "insert into api_product(product_id,product_name,product_model,product_brand," +
            "product_ratings,product_category,product_sub_category,is_available,is_comparable,spec_available,review_available) " +
            "values(:product_id,:product_name,:product_model,:product_brand,:product_ratings,:product_category,:product_sub_category," +
            ":is_available,:is_comparable,:spec_available,:review_available)";

    @Transactional
    private void saveProduct(Product data) {
        Map namedParameters = new HashMap();
        namedParameters.put("product_id", data.getProduct_id());
        namedParameters.put("product_name", data.getProduct_name());
        namedParameters.put("product_model", data.getProduct_model() );
        namedParameters.put("product_brand", data.getProduct_brand() );
        namedParameters.put("product_ratings", data.getProduct_ratings());
        namedParameters.put("product_category", data.getProduct_category());
        namedParameters.put("product_sub_category", data.getProduct_sub_category() );
        namedParameters.put("is_available", data.getIs_comparable());
        namedParameters.put("is_comparable", data.getIs_comparable());
        namedParameters.put("spec_available", data.getSpec_available());
        namedParameters.put("review_available", data.getReview_available() );
        namedParameterJdbcTemplate.update(SAVE_PRODUCT, namedParameters);
    }

    final String SAVE_COLORS = "insert into api_product_color(product_id,color) values(:product_id,:color)";
    @Transactional
    private void saveProductColor(Product data) {
        if(data.getAvailable_colors().size() > 0){
            return;
        }
        for(String color : data.getAvailable_colors()){
            Map namedParameters = new HashMap();
            namedParameters.put("product_id", data.getProduct_id());
            namedParameters.put("color", color);
            namedParameterJdbcTemplate.update(SAVE_COLORS, namedParameters);
        }
    }
    final String SAVE_IMAGES = "insert into api_product_images(product_id,image_path) values(:product_id,:image_path)";
    @Transactional
    private void saveProductImage(Product data) {
        if(data.getProduct_images().size() > 0){
            return;
        }
        for(String image : data.getProduct_images()){
            Map namedParameters = new HashMap();
            namedParameters.put("product_id", data.getProduct_id());
            namedParameters.put("image_path", image);
            namedParameterJdbcTemplate.update(SAVE_IMAGES, namedParameters);
        }
    }

    final String SAVE_STORE = "insert into api_product_store (product_id, product_store, product_store_logo, product_store_url," +
            " product_price, product_offer, product_color, product_delivery, product_delivery_cost, is_emi, is_cod, return_time)" +
            " values(:product_id, :product_store, :product_store_logo, :product_store_url," +
            " :product_price, :product_offer, :product_color, :product_delivery, :product_delivery_cost, :is_emi, :is_cod, :return_time)";

    private void saveStore(Product data) {
        if(data.getStores().size() > 0){
            return;
        }
        for(Store store : data.getStores()){
            EcommercePlatform platform = store.getEcommercePlatform();
            Map namedParameters = new HashMap();
            namedParameters.put("product_id", data.getProduct_id());
            namedParameters.put("product_store", platform.getProduct_store() );
            namedParameters.put("product_store_logo", platform.getProduct_store_logo() );
            namedParameters.put("product_store_url", platform.getProduct_store_url() );
            namedParameters.put("product_price", platform.getProduct_price() );
            namedParameters.put("product_offer", platform.getProduct_offer() );
            namedParameters.put("product_color", platform.getProduct_color() );
            namedParameters.put("product_delivery", platform.getProduct_delivery() );
            namedParameters.put("product_delivery_cost", platform.getProduct_delivery_cost() );
            namedParameters.put("is_emi", platform.getIs_emi() );
            namedParameters.put("is_cod", platform.getIs_cod() );
            namedParameters.put("return_time", platform.getReturn_time() );
            namedParameterJdbcTemplate.update(SAVE_IMAGES, namedParameters);
        }

    }

    public void persistProductSpecs(ProductSpecs specs, String productId) {
        List mainSpecs = specs.getData().getMain_specs();
        Map<String, Object> subSpecs = specs.getData().getSub_specs();

        saveMainSpecs(mainSpecs, productId);
        String specsKey;
        String specsValue;
        for(String category : subSpecs.keySet()){
            List<Map> specList = (ArrayList)subSpecs.get(category);
            for(Map map : specList){
                specsKey = (String)map.get("spec_key");
                specsValue = (String)map.get("spec_value");
                saveSubSpecs(category, specsKey, specsValue, productId);
            }
        }
    }
    final String SAVE_PRODUCT_SEC_SPECS = "insert into api_product_sec_specs(product_id,category,spec_key,spec_value) values(:product_id,:category,:spec_key, :spec_value)";
    @Transactional
    private void saveSubSpecs(String category, String specsKey, String specsValue, String productId) {
        Map namedParameters = new HashMap();
        namedParameters.put("product_id", productId);
        namedParameters.put("category", category );
        namedParameters.put("spec_key", specsKey );
        namedParameters.put("spec_value", specsValue );
        jdbcTemplate.update(SAVE_PRODUCT_SEC_SPECS, namedParameters);
    }
    final String SAVE_PRODUCT_MAIN_SPECS = "insert into api_product_main_specs(product_id,main_specs) values(:product_id,:main_specs)";
    @Transactional
    private void saveMainSpecs(List<String> mainSpecs, String productId) {
        if(mainSpecs.size() > 0){
            return;
        }
        for(String mainSpec : mainSpecs){
            Map namedParameters = new HashMap();
            namedParameters.put("product_id", productId);
            namedParameters.put("main_specs", mainSpec);
            namedParameterJdbcTemplate.update(SAVE_PRODUCT_MAIN_SPECS, namedParameters);
        }

    }
}
