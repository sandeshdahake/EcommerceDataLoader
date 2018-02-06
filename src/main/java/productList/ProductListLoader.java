package productList;

import common.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class ProductListLoader implements Loader{
    private static final Logger log = LoggerFactory.getLogger(ProductListLoader.class);
    String url_category = "https://price-api.datayuge.com/api/v1/compare/list/categories?page=1";

    public  CategoryList callCategoryListService(RestTemplate restTemplate, String url){
        CategoryList categoryList = restTemplate.getForObject(url + "&api_key="+apiKey, CategoryList.class);
        return categoryList;

    }

    @Override
    public void load() {
        RestTemplate restTemplate = new RestTemplate();
        CategoryList list  = callCategoryListService(restTemplate, url_category);
        PersistCategoryList persistCategoryList = new PersistCategoryList();
        persistCategoryList.persist(list);
        while(list.getNext_page_url() != null){
            list =  callCategoryListService(restTemplate, list.getNext_page_url());
            persistCategoryList.persist(list);
            log.info(list.toString());
        }

    }



}
