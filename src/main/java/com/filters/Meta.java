package com.filters;


import java.util.HashMap;
import java.util.Map;

public class Meta {

    private String category;
    private Boolean is_comparable;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIs_comparable() {
        return is_comparable;
    }

    public void setIs_comparable(Boolean is_comparable) {
        this.is_comparable = is_comparable;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}