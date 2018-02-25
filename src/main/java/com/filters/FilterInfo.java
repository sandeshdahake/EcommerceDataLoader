package com.filters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterInfo {

    private Meta meta;
    private List<Map<String, Object>> filters = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public List<Map<String, Object>> getFilters() {
        return filters;
    }

    public void setFilters(List<Map<String, Object>> filters) {
        this.filters = filters;
    }


    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}