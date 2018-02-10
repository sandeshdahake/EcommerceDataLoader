
package com.productList;

import java.util.HashMap;
import java.util.Map;

public class ProductSpecs {

    private ProductSpecificationList data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ProductSpecificationList getData() {
        return data;
    }

    public void setData(ProductSpecificationList data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}