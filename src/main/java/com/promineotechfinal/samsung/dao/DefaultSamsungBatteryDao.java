package com.promineotechfinal.samsung.dao;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import com.promineotechfinal.samsung.entity.Battery;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultSamsungBatteryDao implements SamsungBatteryDao {
  
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public boolean updateBattery(Battery battery) {
    //@formatter:off
    String sql = """
            UPDATE batteries SET
            battery_capacity=:battery_capacity,
            manufacturer=:manufacturer,
            price=:price,
            warranty_days=:warranty_days
        WHERE customer_pk=:customer_pk
            """;
    //@formatter:on
    
    Map<String, Object> params = new HashMap<>();
    Map.of("battery_capacity", battery.getBatteryCapacity(), "manufacturer"
        ,battery.getManufacturer(),"price", battery.getPrice(),"warranty_days",battery.getWarrantyDays());
    
    
    return jdbcTemplate.update(sql, params) ==1;
  }

}
