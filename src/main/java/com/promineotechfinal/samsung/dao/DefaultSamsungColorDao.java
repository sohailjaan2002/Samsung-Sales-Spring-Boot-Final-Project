package com.promineotechfinal.samsung.dao;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.promineotechfinal.samsung.entity.Color;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultSamsungColorDao implements SamsungColorDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  @Override
  public boolean updateColor(Color color) {
    //@formatter"off
    String sql = """
            UPDATE colors SET
            color= :color,
            price= :price,
            is_exterior= :is_exterior
        WHERE color_pk= :color_pk
            """;
    //@formatter"on
Map<String, Object> params = new HashMap<>();
Map.of("color", color.getColor(), "price", color.getPrice(), "is_exterior", color.isExterior());
    return jdbcTemplate.update(sql, params) ==1;
  }

}
