package com.images;

import ch.qos.logback.core.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ImageLoader {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Value("${image.download.path}")
     String osPath;
    public void downloadImage(Map<String, Object> image){
      int id = (int)image.get("ID");
      String productId = (String)image.get("product_id");
      String url=(String)image.get("image_path");
      String subCategoryName=(String)image.get("product_sub_category");

      String fileName= productId.trim()+ "_"+ id + ".jpg" ;
      String filePath = subCategoryName.trim()+"/full/"+fileName ;
      File file = new File(osPath+filePath);
      file.getParentFile().mkdirs();
      URL myUrl = null;
        try {
            myUrl = new URL(url);
            InputStream in = myUrl.openStream();
            OutputStream out = new FileOutputStream(file);
            FileCopyUtils.copy(in, out );
            updateFileProcessod(id, filePath);
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateFileProcessod(int id, String path) {
        String sql = " update api_product_images set Image_local_path ='" + path   +"' , image_loaded = 1 where id=" + id;
        namedParameterJdbcTemplate.update(sql, new HashMap<>());
    }


    public void getImageListToDownload(){
       String sql =
               " select i.*, p.product_sub_category from api_product_images i\n" +
               " inner join api_product p on i.product_id = p.product_id where i.image_loaded = 0 \n";
       Map map = new HashMap();
       List<Map<String, Object>> images = namedParameterJdbcTemplate.queryForList(sql,map);

       for(Map<String, Object> image : images){
           downloadImage(image);
       }
    }

}
