package com.categoryList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategoryRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    final String sql = "INSERT INTO api_category(category_id,category,category_name,sub_category,sub_category_name" +
            ",child_category,child_category_name,child_property,can_compare,created_at,updated_at,isLoaded)" +
            "VALUES (:id,:category,:category_name,:sub_category,:sub_category_name"  +
            " ,:child_category,:child_category_name, :child_property,:can_compare,:created_at,:updated_at,0)";

    final String emptyChildHandel = "update api_category\n" +
            "set child_category = sub_category,\n" +
            "child_category_name = sub_category_name \n" +
            "where child_category is null;";

    public void persist(List<ProductCategory> data) {
      //  emptyCategoryTable();

        insertBatch(data);
    }

    public void emptyCategoryTable() {
        jdbcTemplate.execute("truncate api_category ");
    }

    public void setEmptyChildAsSubCat() {
        jdbcTemplate.execute(emptyChildHandel);
    }

    public void insertBatch(final List<ProductCategory> categories) {
        SqlParameterSource[] parameterSource = SqlParameterSourceUtils.createBatch(categories.toArray());
        try {
            namedParameterJdbcTemplate.batchUpdate(sql, parameterSource);
        }
        catch(DuplicateKeyException e){
    }
    }

    public List<String> getUnProcessedCategory(boolean isFashion){
        if(isFashion){
            return jdbcTemplate.queryForList("select child_category from api_category where isLoaded = 0 and can_compare =0 ", String.class);

        }else{
            return jdbcTemplate.queryForList("select child_category from api_category where isLoaded = 0 and (can_compare is null or can_compare = 1 )", String.class);

        }
    }

    public void markCategoryProcessed(String categoryName){
        jdbcTemplate.execute("update api_category set isLoaded=1 where child_category ='" + categoryName + "'");
    }
}