package com.productList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductSpecificationList {
    private List<String> main_specs = null;

    public Map<String, Object> getSub_specs() {
        return sub_specs;
    }

    public void setSub_specs(Map<String, Object> sub_specs) {
        this.sub_specs = sub_specs;
    }

    private Map<String, Object> sub_specs;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<String> getMain_specs() {
        return main_specs;
    }

    public void setMain_specs(List<String> main_specs) {
        this.main_specs = main_specs;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
