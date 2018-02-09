package com.productList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private String product_name;
    private String product_model;
    private String product_brand;
    private String product_id;
    private String product_ratings;
    private String product_category;
    private String product_sub_category;
    private Boolean is_available;
    private List<String> available_colors = null;
    private List<String> product_images = null;
    private Boolean is_comparable;
    private Boolean spec_available;
    private Boolean review_available;
    private List<Store> stores = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

    public String getProduct_brand() {
        return product_brand;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_ratings() {
        return product_ratings;
    }

    public void setProduct_ratings(String product_ratings) {
        this.product_ratings = product_ratings;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_sub_category() {
        return product_sub_category;
    }

    public void setProduct_sub_category(String product_sub_category) {
        this.product_sub_category = product_sub_category;
    }

    public Boolean getIs_available() {
        return is_available;
    }

    public void setIs_available(Boolean is_available) {
        this.is_available = is_available;
    }

    public List<String> getAvailable_colors() {
        return available_colors;
    }

    public void setAvailable_colors(List<String> available_colors) {
        this.available_colors = available_colors;
    }

    public List<String> getProduct_images() {
        return product_images;
    }

    public void setProduct_images(List<String> product_images) {
        this.product_images = product_images;
    }

    public Boolean getIs_comparable() {
        return is_comparable;
    }

    public void setIs_comparable(Boolean is_comparable) {
        this.is_comparable = is_comparable;
    }

    public Boolean getSpec_available() {
        return spec_available;
    }

    public void setSpec_available(Boolean spec_available) {
        this.spec_available = spec_available;
    }

    public Boolean getReview_available() {
        return review_available;
    }

    public void setReview_available(Boolean review_available) {
        this.review_available = review_available;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
