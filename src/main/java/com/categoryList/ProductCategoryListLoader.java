package com.categoryList;

import com.common.Loader;
import com.common.SlackPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class ProductCategoryListLoader implements Loader{
    private static final Logger log = LoggerFactory.getLogger(ProductCategoryListLoader.class);
    @Autowired
    RestOperations restTemplate ;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SlackPublisher slackPublisher;


    String url_category = "https://price-api.datayuge.com/api/v1/compare/list/categories?page=1";

    public  CategoryList callCategoryListService(String url){
        log.info("calling categoy service: ",url);
        CategoryList categoryList = restTemplate.getForObject(url + "&api_key="+API_KEY, CategoryList.class);
        return categoryList;

    }

    @Override
    public void load(Object object) {
        CategoryList list  = callCategoryListService(url_category);
        categoryRepository.emptyCategoryTable();
        categoryRepository.persist(list.getData());
        while(list.getNext_page_url() != null){
            list =  callCategoryListService(list.getNext_page_url());
            categoryRepository.persist(list.getData());
            try {
                Thread.sleep(WAIT);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("########### Error while loadig category : Thread interuppted");
                slackPublisher.publish("Error while loadig category  : Thread interuppted");
                slackPublisher.publish(e.getMessage());

            }
            log.info(list.toString());
        }
        categoryRepository.setEmptyChildAsSubCat();

    }

    public List<String> getUnProcessedCategory(){
        return categoryRepository.getUnProcessedCategory();
    }
}
