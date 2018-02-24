package com.categoryList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    final String sql = "INSERT INTO comparetest.api_category(category_id,category,category_name,sub_category,sub_category_name" +
            ",child_category,child_category_name,child_property,can_compare,created_at,updated_at,isLoaded)" +
            "VALUES (:id,:category,:category_name,:sub_category,:sub_category_name"  +
            " ,:child_category,:child_category_name, :child_property,:can_compare,:created_at,:updated_at,0)";

    public void persist(List<ProductCategory> data) {
      //  emptyCategoryTable();
        insertBatch(data);
    }

    public void emptyCategoryTable() {
        jdbcTemplate.execute("truncate api_category ");
    }

    public void insertBatch(final List<ProductCategory> categories) {
        SqlParameterSource[] parameterSource = SqlParameterSourceUtils.createBatch(categories.toArray());
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSource);
    }

    public List<String> getUnProcessedCategory(){
        return jdbcTemplate.queryForList("select child_category from api_category where isLoaded = 0", String.class);
    }

    public void markCategoryProcessed(String categoryName){
        jdbcTemplate.execute("update api_category set isLoaded=1 where child_category ='" + categoryName + "'");
    }
}