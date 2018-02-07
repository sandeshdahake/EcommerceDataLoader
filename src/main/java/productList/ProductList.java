package productList;
import java.util.HashMap;
import java.util.Map;
public class ProductList {
    private String product_title;
    private Boolean can_compare;
    private Integer product_lowest_price;
    private String product_link;
    private String product_id;
    private String product_category;
    private String product_sub_category;
    private Double product_rating;
    private String product_image;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public Boolean getCan_compare() {
        return can_compare;
    }

    public void setCan_compare(Boolean can_compare) {
        this.can_compare = can_compare;
    }

    public Integer getProduct_lowest_price() {
        return product_lowest_price;
    }

    public void setProduct_lowest_price(Integer product_lowest_price) {
        this.product_lowest_price = product_lowest_price;
    }

    public String getProduct_link() {
        return product_link;
    }

    public void setProduct_link(String product_link) {
        this.product_link = product_link;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    public Double getProduct_rating() {
        return product_rating;
    }

    public void setProduct_rating(Double product_rating) {
        this.product_rating = product_rating;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
