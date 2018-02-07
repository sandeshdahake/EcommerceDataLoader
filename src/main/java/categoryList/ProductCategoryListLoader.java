package categoryList;

import common.Loader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class ProductCategoryListLoader implements Loader{
    private static final Logger log = LoggerFactory.getLogger(ProductCategoryListLoader.class);
    String url_category = "https://price-api.datayuge.com/api/v1/compare/list/categories?page=1";

    public  CategoryList callCategoryListService(RestTemplate restTemplate, String url){
        log.info("calling categoy service: ",url);
        CategoryList categoryList = restTemplate.getForObject(url + "&api_key="+API_KEY, CategoryList.class);
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
            try {
                Thread.sleep(WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("########### Error while loadig category : Thread interuppted");
            }
            log.info(list.toString());
        }

    }



}
