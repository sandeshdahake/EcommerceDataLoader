package com.common;

import com.productList.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ApiHitCounterService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);
    boolean init = true;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    SimpleDateFormat format = new SimpleDateFormat("MMyyyy");

    public void setCount(int count) {
        this.count = count;
    }

    private int count;

    @Autowired
    SlackPublisher slackPublisher;

    public int getCount() {
        return count;
    }

    public void incrementCounter() {
        count++;
        if(count%10 == 0){
            persistCount(count);
        }
    }

    private void persistCount(int count) {
        int counter =  0;

        if(init){
             counter =  getCount() + count;
             init = false;
        }
         counter =  count;
        slackPublisher.publish("Total API hits count -  " + counter );

        Map namedParameters = new HashMap();
        namedParameters.put("create_date", format.format(new Date()));
        namedParameters.put("api_count",counter );
        namedParameterJdbcTemplate.update(" update api_counter set api_count=:api_count where create_date = :create_date", namedParameters);
    }

    public int getApiCount() {
        Map namedParameters = new HashMap();
        namedParameters.put("create_date", format.format(new Date()));
        int  dbCount = namedParameterJdbcTemplate.queryForObject("select  api_count from api_counter where create_date = :create_date", namedParameters, Integer.class);
        return dbCount;
    }

}
