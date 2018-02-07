package productList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListForCategory {
    private List<ProductList> data = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public List<ProductList> getData() {
        return data;
    }

    public void setData(List<ProductList> data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
