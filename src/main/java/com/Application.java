package com;

import com.categoryList.ProductCategoryListLoader;
import com.common.ApiHitCounterService;
import com.common.Loader;
import com.common.SlackPublisher;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public ProductCategoryListLoader productCategoryListLoader;

    @Autowired
    ProductListLoader productListLoader;


    @Value("${load.option}")
    private String runOption;

    @Autowired
    SlackPublisher slackPublisher;

    void  loadProductCategories(){
        productCategoryListLoader.load(null);
    }

     void loadProdctsInfo(){
        List<String> categoryList = productCategoryListLoader.getUnProcessedCategory();
        for(String category : categoryList){
            slackPublisher.publish("Category load started for -" + category);
            productListLoader.load(category);
            productListLoader.loadProductFiltersByCateory(category);
            slackPublisher.publish("Category load completed for -" + category);
        }
    }

    public static void main(String args[]) {
        ApplicationContext ctx = SpringApplication.run(Application.class,args);
    }

    @Override
    public void run(ApplicationArguments args)  {
       try{
         if(runOption.equals("category")){
             slackPublisher.publish("Category load started");
             loadProductCategories();
             slackPublisher.publish("Category load completed");
         }else if(runOption.equals("product")){
             slackPublisher.publish("Product load started");
             loadProdctsInfo();
             slackPublisher.publish("product load completed");
         }
       }catch (Exception e){
          slackPublisher.publish("Data load stopped because of exception" );
          slackPublisher.publish(e.getMessage());
       }
    }
}
