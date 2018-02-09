package com.productList;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.HashMap;
import java.util.Map;
public class EcommercePlatform {
    private String product_store;
    private String product_store_logo;
    private String product_store_url;
    private String product_price;
    private String product_offer;
    private String product_color;
    private String product_delivery;
    private String product_delivery_cost;
    private String is_emi;
    private String is_cod;
    private String return_time;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getProduct_store() {
        return product_store;
    }

    public void setProduct_store(String product_store) {
        this.product_store = product_store;
    }

    public String getProduct_store_logo() {
        return product_store_logo;
    }

    public void setProduct_store_logo(String product_store_logo) {
        this.product_store_logo = product_store_logo;
    }

    public String getProduct_store_url() {
        return product_store_url;
    }

    public void setProduct_store_url(String product_store_url) {
        this.product_store_url = product_store_url;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_offer() {
        return product_offer;
    }

    public void setProduct_offer(String product_offer) {
        this.product_offer = product_offer;
    }

    public String getProduct_color() {
        return product_color;
    }

    public void setProduct_color(String product_color) {
        this.product_color = product_color;
    }

    public String getProduct_delivery() {
        return product_delivery;
    }

    public void setProduct_delivery(String product_delivery) {
        this.product_delivery = product_delivery;
    }

    public String getProduct_delivery_cost() {
        return product_delivery_cost;
    }

    public void setProduct_delivery_cost(String product_delivery_cost) {
        this.product_delivery_cost = product_delivery_cost;
    }

    public String getIs_emi() {
        return is_emi;
    }

    public void setIs_emi(String is_emi) {
        this.is_emi = is_emi;
    }

    public String getIs_cod() {
        return is_cod;
    }

    public void setIs_cod(String is_cod) {
        this.is_cod = is_cod;
    }

    public String getReturn_time() {
        return return_time;
    }

    public void setReturn_time(String return_time) {
        this.return_time = return_time;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
