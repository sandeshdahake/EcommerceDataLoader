package com;

import com.categoryList.ProductCategoryListLoader;
import com.common.ApiHitCounterService;
import com.common.Loader;
import com.common.SlackPublisher;
import com.database.SQLAutomation;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.images.ImageLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import com.productList.ProductListLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@ComponentScan("com.*")
public class Application implements ApplicationRunner {
    /*@Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }*/
    @Bean
    public RestOperations restTemplate() {
        RestTemplate rest = new RestTemplate();
        //this is crucial!
        rest.getMessageConverters().add(0, mappingJacksonHttpMessageConverter());
        return rest;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        return converter;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
        return objectMapper;
    }

    @Autowired
    ApiHitCounterService apiHitCounterService;

    @Autowired
    public ProductCategoryListLoader productCategoryListLoader;

    @Autowired
    ProductListLoader productListLoader;
    @Autowired
    SQLAutomation sqlAutomation;

    @Autowired
    ImageLoader imageLoader;


    @Value("${load.option}")
    private String runOption;

    @Autowired
    SlackPublisher slackPublisher;

    void  loadProductCategories(){
        productCategoryListLoader.load(null);
    }

     void loadProdctsInfo(boolean isFashion){
        List<String> categoryList = productCategoryListLoader.getUnProcessedCategory(isFashion);
        for(String category : categoryList){
            slackPublisher.publish("Category load started for -" + category);
            productListLoader.load(category, isFashion);
            productListLoader.loadProductFiltersByCateory(category);
            slackPublisher.publish("Category load completed for -" + category);
        }
    }

    public static void main(String args[]) {
        ApplicationContext ctx = SpringApplication.run(Application.class,args);
    }

    @Override
    public void run(ApplicationArguments args)  {
        apiHitCounterService.setCount(apiHitCounterService.getApiCount());
       try{
         if(runOption.equals("category")){
             slackPublisher.publish("Category load started");
             loadProductCategories();
             slackPublisher.publish("Category load completed");
         }else if(runOption.equals("product")){
             slackPublisher.publish("Product load started");
             loadProdctsInfo(false);
             slackPublisher.publish("product load completed");
         }else if(runOption.equals("image")){
             slackPublisher.publish("Image load started");
             imageLoader.getImageListToDownload();
             slackPublisher.publish("Image load completed");
         }else if(runOption.equals("loadSpecs")){
             slackPublisher.publish(" load missing specification started");
             productListLoader.getMissingSpecification();
             slackPublisher.publish("load missing specification completed");

         }else if(runOption.equals("runSP")){
             slackPublisher.publish(" started SP creation using SP");
             sqlAutomation.runSP();
             slackPublisher.publish(" stopped SP creation using SP");

         }else if(runOption.equals("product-fashion")){
             slackPublisher.publish("Product load started");
             loadProdctsInfo(true);
             slackPublisher.publish("product load completed");
         }
       }catch (Exception e){
          slackPublisher.publish("Data load stopped because of exception" );
          slackPublisher.publish(e.getMessage());
       }
    }
}
