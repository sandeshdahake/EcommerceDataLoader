package com.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class SqlAutomationRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(SqlAutomationRepository.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    final String GET_SUBCATEGORY_ID = "SELECT id FROM productsubcategories";
    @Transactional
    private void saveProductImage() {
        Map namedParameters = new HashMap();
       List<Integer> subCatList =  namedParameterJdbcTemplate.queryForList(GET_SUBCATEGORY_ID, namedParameters,Integer.class );

       if(subCatList.size() > 0){
           for(Integer subcat:subCatList){
               createProductTableSP(subcat);
               insertInProductTableSP(subcat);
           }
        }
    }

    void createProductTableSP(Integer subcat){
        Map namedParameters = new HashMap();
        String  sql = "call create_product_table("+subcat+")";
        namedParameterJdbcTemplate.update(sql,namedParameters );
    }

    void insertInProductTableSP(Integer subcat){
        String  sql = "call setup_product_table("+subcat+")";
        Map namedParameters = new HashMap();
        namedParameterJdbcTemplate.update(sql,namedParameters );
    }

}
