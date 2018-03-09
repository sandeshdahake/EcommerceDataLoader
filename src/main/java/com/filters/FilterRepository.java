package com.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FilterRepository {
    final String SAVE_FILTER = "insert into api_filters(ProductSubcategoryName,Name,Type,ParentSubcategoryId) values(:ProductSubcategoryName,:Name,:Type,:ParentSubcategoryId)";
    final String SAVE_FILTER_DETAILS = "insert into API_filterdetails (FilterId,FilterMinValue,FilterMaxValue,Priority,Value) " +
            "values(:FilterId, :FilterMinValue, :FilterMaxValue, :Priority, :Value)";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(FilterRepository.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Transactional
    public void persistFilter(FilterInfo filterInfo) {
        Meta meta = filterInfo.getMeta();

        for(Map<String, Object> filter:filterInfo.getFilters()){
            String title= (String)filter.get("title");
            String type= (String)filter.get("type");
            List<Map<String, Object>> listOfFilterDetails  = (List<Map<String, Object>>)filter.get("contents");
            try {
                int filterId = saveFilter(meta.getCategory(),title, type);
                for(Map<String, Object> filterDetail:listOfFilterDetails){
                    saveFilterDetails( filterId, filterDetail, type, title.toLowerCase());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                log.info("Duplicate filter loading next filter");

            }catch(DuplicateKeyException e){
               // e.printStackTrace();
                log.info("Duplicate filter loading next filter");
            }
            catch(NullPointerException e){
                // e.printStackTrace();
                log.info("Null pointer exception while loading filter.. loading next filter " );
            }
        }

    }
    @Transactional
    private void saveFilterDetails(int filterId, Map<String, Object> filterDetail, String type, String title) {
        String minValue = "0";
        String maxValue= "0";
        String value= null;
        String minValueStr = title+"_"+"start";
        String maxValueStr= title+"_"+"end";
        if(type.equalsIgnoreCase("slider")){
            minValue =(String)filterDetail.get(minValueStr);
            maxValue =(String)filterDetail.get(maxValueStr);
            value =(String)filterDetail.get("name");
        }else {
            value =(String)filterDetail.get("name");
        }
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("FilterId",filterId );
        namedParameters.addValue("FilterMinValue", minValue );
        namedParameters.addValue("FilterMaxValue", maxValue);
        namedParameters.addValue("Priority",0 );
        namedParameters.addValue("Value", value );
        namedParameterJdbcTemplate.update(SAVE_FILTER_DETAILS, namedParameters);
    }

    @Transactional
    private int saveFilter(String categoryName,String filterName, String filterType ) throws SQLException,DuplicateKeyException {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("ProductSubcategoryName",categoryName );
        namedParameters.addValue("Name",filterName );
        namedParameters.addValue("Type", filterType  );
        namedParameters.addValue("ParentSubcategoryId", 0 );
        final KeyHolder holder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(SAVE_FILTER, namedParameters, holder, new String[] {"id" });
        Number generatedId = holder.getKey();
        return generatedId.intValue();
    }
}
