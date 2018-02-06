package productList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ProductCategory {
    private Integer id;
    private String category;
    private String category_name;
    private String sub_category;
    private String sub_category_name;
    private String child_category;
    private String child_category_name;
    private Object child_property;
    private Integer can_compare;
    private String created_at;
    private String updated_at;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSub_category() {
        return sub_category;
    }

    public void setSub_category(String sub_category) {
        this.sub_category = sub_category;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    public String getChild_category() {
        return child_category;
    }

    public void setChild_category(String child_category) {
        this.child_category = child_category;
    }

    public String getChild_category_name() {
        return child_category_name;
    }

    public void setChild_category_name(String child_category_name) {
        this.child_category_name = child_category_name;
    }

    public Object getChild_property() {
        return child_property;
    }

    public void setChild_property(Object child_property) {
        this.child_property = child_property;
    }

    public Integer getCan_compare() {
        return can_compare;
    }

    public void setCan_compare(Integer can_compare) {
        this.can_compare = can_compare;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("category", category).append("category_name", category_name).append("sub_category", sub_category).append("sub_category_name", sub_category_name).append("child_category", child_category).append("child_category_name", child_category_name).append("child_property", child_property).append("can_compare", can_compare).append("created_at", created_at).append("updated_at", updated_at).append("additionalProperties", additionalProperties).toString();
    }

}
