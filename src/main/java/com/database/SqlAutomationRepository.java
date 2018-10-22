package com.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
    private void createProductData() {
        Map namedParameters = new HashMap();
       List<Integer> subCatList =  namedParameterJdbcTemplate.queryForList(GET_SUBCATEGORY_ID, namedParameters,Integer.class );

       if(subCatList.size() > 0){
           for(Integer subcat:subCatList){
               createProductTableSP(subcat);
               insertInProductTableSP(subcat);
           }
        }
    }

    @Value("${sql.file.path}")
    String  filePath  ;

    void automateProductSpecs(){
        try {

            File product_specFile = new File(filePath+"api_product_sec_sepcs_sp.sql");
            File category_spFile = new File(filePath+"api_category_sp.sql");
            File productcategories = new File(filePath+"productcategories.sql");
            File products = new File(filePath+"products.sql");
            File productspecs = new File(filePath+"productspecs.sql");
            File restSql = new File(filePath+"restSql.sql");
            log.info("sql load started");
            log.info("running product_specFile sql");
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new FileSystemResource(product_specFile));
            log.info("running category_spFile sql");
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new FileSystemResource(category_spFile));
            log.info("running productcategories sql");
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new FileSystemResource(productcategories));
            log.info("running products sql");
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new FileSystemResource(products));
            log.info("running productspecs sql");
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new FileSystemResource(productspecs));
            log.info("running restSql sql");
            ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), new FileSystemResource(restSql));
            createProductData();

        } catch (InvalidResultSetAccessException e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        catch (DataAccessException e)
        {
            log.error(e.getMessage());
            e.printStackTrace();
        }catch (SQLException e) {
            log.error(e.getMessage());
            e.printStackTrace();
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
