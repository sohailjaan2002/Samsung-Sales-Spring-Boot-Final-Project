package com.promineotechfinal.samsung.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import com.promineotechfinal.samsung.entity.Image;
import com.promineotechfinal.samsung.entity.ImageMimeType;
import com.promineotechfinal.samsung.entity.Samsung;
import com.promineotechfinal.samsung.entity.SamsungModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DefaultSamsungSalesDao implements SamsungSalesDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  @Override
  public List<Samsung> fetchSamsung(SamsungModel model, String type) {
    log.info("DAO: model={}, type={}", model, type);
    
 // @formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM models "
        + "WHERE model_id = :model_id  AND type_model = :type_model";
        //+ "AND type_level = :type_level";
    // @formatter:on
    
    Map<String, Object> params = new HashMap<>();
    params.put("model_id", model.toString());
    params.put("type_model", type);
    
    return jdbcTemplate.query(sql, params, new RowMapper<>() {

      @Override
      public Samsung mapRow(ResultSet rs, int rowNum) throws SQLException {
     // @formatter:off
        return Samsung.builder()
            .launchPrice(new BigDecimal(rs.getString("launch_price")))
            .modelId(SamsungModel.valueOf(rs.getString("model_id")))
            .modelPK(rs.getLong("model_pk"))
            .storageInGB(rs.getInt("storage_in_GB"))
            .typeModel(rs.getString("type_model"))
            .screenSize(rs.getInt("screen_size"))
            .build();
     // @formatter :on
      }});
  }

  @Override
  public void saveImage(Image image) {
    
    String sql = ""
        + "INSERT INTO images ("
        + "model_fk, image_id, width, height, mime_type, name, data"
        + ") VALUES ("
        + ":model_fk, :image_id, :width, :height, :mime_type, :name, :data"
        + ")";
    Map<String, Object> params = new HashMap<>();
    params.put("model_fk", image.getModelFK());
    params.put("image_id", image.getImageId());
    params.put("width", image.getWidth());
    params.put("height", image.getHeight());
    params.put("mime_type", image.getMimeType().getMimeType());
    params.put("name", image.getName());
    params.put("data", image.getData());
    
    jdbcTemplate.update(sql, params);
  }

  @Override
  public Optional<Image> retrieveImage(String imageId) {
    String sql = ""
        + "SELECT * "
        + "FROM images "
        + "WHERE image_id = :image_id";
    
    Map<String, Object> params = new HashMap<>();
    params.put("image_id", imageId);
    return jdbcTemplate.query(sql, params, new ResultSetExtractor<>() {

      @Override
      public Optional<Image> extractData(ResultSet rs) 
          throws SQLException {
        if(rs.next()) {
          //@formatter:off
          return Optional.of(Image.builder()
              .imagePK(rs.getLong("image_pk"))
              .modelFK(rs.getLong("model_fk"))
              .imageId(rs.getString("image_id"))
              .width(rs.getInt("width"))
              .height(rs.getInt("height"))
              .mimeType(ImageMimeType.fromString(rs.getString("mime_type")))
              .name(rs.getString("name"))
              .data(rs.getBytes("data"))
              .build());
              
        //@formatter:on
        }
        return Optional.empty();
      }});
  }

  
  }


