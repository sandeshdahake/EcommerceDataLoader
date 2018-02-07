package productList;

import com.fasterxml.jackson.databind.JsonMappingException;
import common.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

public class ProductListLoader implements Loader {
    private static final Logger log = LoggerFactory.getLogger(ProductListLoader.class);
    String url_product_list_by_category ="https://price-api.datayuge.com/api/v1/compare/list";
    RestTemplate restTemplate = new RestTemplate();
    @Override
    public void load() {

    }
    public ProductListForCategory CallProductListByCategoryService(String url, String category) throws HttpMessageNotReadableException{
        log.info("calling Product list for category : " + category,url);
        ProductListForCategory list = restTemplate.getForObject(url + "?api_key="+API_KEY+"&sub_category=" + category, ProductListForCategory.class);
        return list;

    }
    public void load(String category) {
        ProductListForCategory productListForCategory = null;
        try {
            productListForCategory = CallProductListByCategoryService(url_product_list_by_category,category);
            for (ProductList item : productListForCategory.getData()){
                log.info("##############"+ item.getProduct_id() + item.getProduct_title() + "##############");
                loadProductByProductId(item.getProduct_id());
                loadProductSpecsByProductId(item.getProduct_id());
            }
        } catch (HttpMessageNotReadableException ee){
            log.error("XXXXXXXXXX Jason Mappig error for given category ignoring and moving ahead XXXXXXXXXXX");

        }

    }

    private void loadProductSpecsByProductId(String product_id) {
    }

    private void loadProductByProductId(String product_id) {
    }
}
