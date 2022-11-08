package com.promineotechfinal.samsung.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.promineotechfinal.samsung.entity.Battery;
import com.promineotechfinal.samsung.entity.Color;
import com.promineotechfinal.samsung.entity.Customer;
import com.promineotechfinal.samsung.entity.Option;
import com.promineotechfinal.samsung.entity.OptionType;
import com.promineotechfinal.samsung.entity.Order;
import com.promineotechfinal.samsung.entity.Processor;
import com.promineotechfinal.samsung.entity.ProcessorType;
import com.promineotechfinal.samsung.entity.Samsung;
import com.promineotechfinal.samsung.entity.SamsungModel;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultSamsungOrderDao implements SamsungOrderDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;

  @Override
  public Order saveOrder(Customer customer, Samsung samsung, Color color, Processor processor,
      Battery battery, BigDecimal price, List<Option> options) {
    
SqlParams params = generateInsertSql(customer, samsung, color, processor, battery, price); 

  KeyHolder keyHolder = new GeneratedKeyHolder();
  jdbcTemplate.update(params.sql, params.source, keyHolder);
  
  Long orderPK = keyHolder.getKey().longValue();
  
  saveOptions(options, orderPK);
  
  // @formatter: off
  return Order.builder()
      .orderPK(orderPK)
      .customer(customer)
      .model(samsung)
      .color(color)
      .processor(processor)
      .battery(battery)
      .options(options)
      .price(price)
      .build();
  // @formatter: on
  }


  /**
   * @param options
   * @param orderPK
   */
  private void saveOptions(List<Option> options, Long orderPK) {
    for(Option option : options) {
      SqlParams params = generateInserSql(option, orderPK);
      jdbcTemplate.update(params.sql, params.source);
    }
    
  }


  
  private SqlParams generateInserSql(Option option, Long orderPK) {
    
    SqlParams params = new SqlParams();
    // @formatter:off
    String sql = ""
        + "INSERT INTO order_options ("
        + "option_fk, order_fk"
        + ") VALUES ("
        + ":option_fk, :order_fk"
        + ")";
    // @formatter:off
    
    params.source.addValue("option_fk", option.getOptionPK());
    params.source.addValue("order_fk", orderPK);
    params.sql = sql;

    return params;
  }


  private SqlParams generateInsertSql(Customer customer, Samsung samsung, Color color,
      Processor processor, Battery battery, BigDecimal price) {
    // @formatter:off
    String sql= ""
        + "INSERT INTO orders ("
        + "customer_fk, color_fk, processor_fk, battery_fk, model_fk, price"
        + ") VALUES ("
        + ":customer_fk, :color_fk, :processor_fk, :battery_fk, :model_fk, :price"
        + ")";
    //@formatter:on
    SqlParams params = new SqlParams();
    params.sql = sql;
    params.source.addValue("customer_fk", customer.getCustomerPK());
    params.source.addValue("color_fk", color.getColorPK());
    params.source.addValue("processor_fk", processor.getProcessorPK());
    params.source.addValue("battery_fk", battery.getBatteryPK());
    params.source.addValue("model_fk", samsung.getModelPK());
    params.source.addValue("price", price);
    
    return params;
  }

  @Override
  public List<Option> fetchOptions(List<String> optionIds) {
    if (optionIds.isEmpty()) {
      return new LinkedList<>();
    }

    Map<String, Object> params = new HashMap<>();
    //@formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM options "
        + "WHERE option_id IN(";
    //@formatter : on
    for (int index = 0; index < optionIds.size(); index++) {
      String key = "option_" + index;
      sql += ":" + key + ", ";
      params.put(key, optionIds.get(index));
    }
    sql = sql.substring(0, sql.length() -2);
    sql += ")";
    
    return jdbcTemplate.query(sql, params, new RowMapper<Option>() {

      @Override
      public Option mapRow(ResultSet rs, int rowNum) throws SQLException {
        // @formatter :off
        return Option.builder()
           .category(OptionType.valueOf(rs.getString("category"))) 
           .manufacturer(rs.getString("manufacturer"))
           .name(rs.getString("name"))
           .optionId(rs.getString("option_id"))
           .optionPK(rs.getLong("option_pk"))
           .price(rs.getBigDecimal("price"))
            .build();
        //@formatter :on
      }});
  }
  
  @Override
  public Optional<Customer> fetchCustomer(String customerId) {
    String sql = ""
        + "SELECT * "
        + "FROM customers "
        + "WHERE customer_id = :customer_id";
    
    Map<String, Object> params = new HashMap<>();
    params.put("customer_id", customerId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new CustomerResultSetExtractor())); 
  }

  class CustomerResultSetExtractor implements ResultSetExtractor<Customer>{
    @Override
    public Customer extractData(ResultSet rs) 
        throws SQLException, DataAccessException {
      rs.next();
      // @formatter:off
      return Customer.builder()
          .customerId(rs.getString("customer_id"))
          .customerPK(rs.getLong("customer_pk"))
          .firstName(rs.getString("first_name"))
          .lastName(rs.getString("last_name"))
          .phone(rs.getString("phone"))
          .build();
      // @formatter :on
    }

   
}
  @Override
  public Optional<Samsung> fetchModel(SamsungModel model, String type, int storageInGB) {
    // @formatter :off
    String sql = ""
        + "SELECT * "
        + "FROM models "
        + "WHERE model_id = :model_id "
        + "AND type_model = :type_model "
        + "AND storage_in_GB = :storage_in_GB";
    // @formatter:on

    Map<String, Object> params = new HashMap<>();
    params.put("model_id", model.toString());
    params.put("type_model", type);
    params.put("storage_in_GB", storageInGB);
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new ModelResultSetSxtractor()));

  }

  class ModelResultSetSxtractor implements ResultSetExtractor<Samsung> {

    @Override
    public Samsung extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      // @formatter :off
      return Samsung.builder()
          .launchPrice(rs.getBigDecimal("launch_price"))
          .modelId(SamsungModel.valueOf(rs.getString("model_id")))
          .modelPK(rs.getLong("model_pk"))
          .storageInGB(rs.getInt("storage_in_GB"))
          .typeModel(rs.getString("type_model"))
          .screenSize(rs.getInt("screen_size"))
          .build();
      // @formatter :on
    }

  }

  @Override
  public Optional<Color> fetchColor(String colorId) {
    // @formatter :off
    String sql = "" 
        + "SELECT * " 
        + "FROM colors " 
        + "WHERE color_id =:color_id";
    // @formatter :on

    Map<String, Object> params = new HashMap<>();
    params.put("color_id", colorId);
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new ColorResultSetExtractor()));
  }

  class ColorResultSetExtractor implements ResultSetExtractor<Color> {

    @Override
    public Color extractData(ResultSet rs) throws SQLException, DataAccessException {
      rs.next();
      //@formatter:off
      
      return Color.builder()
          
          .colorId(rs.getString("color_id"))
          .colorPK(rs.getLong("color_pk"))
          .color(rs.getString("color"))
          .price(rs.getBigDecimal("price"))
          .isExterior(rs.getBoolean("is_exterior"))
          .build();
    }
    
  }

  @Override
  public Optional<Processor> fetchProcessor(String processorId) {
    //@formatter: off
    String sql = ""
        + "SELECT * "
        + "FROM processors "
        + "WHERE processor_id = :processor_id";
    //@formatter: on
    Map<String, Object> params = new HashMap<>();
    params.put("processor_id", processorId);
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new ProcessorResultSetExtractor()));
  }
  
  class ProcessorResultSetExtractor implements ResultSetExtractor<Processor>{

    @Override
    public Processor extractData(ResultSet rs) 
        throws SQLException, DataAccessException {
      rs.next();
      return Processor.builder()
          .processorPK(rs.getLong("processor_pk"))
          .processorId(rs.getString("processor_id"))
          .processorType(ProcessorType.valueOf(rs.getString("processor_type")))
          .description(rs.getString("description"))
          .hasStartStop(rs.getBoolean("has_start_stop"))
          .speedInGhz(rs.getFloat("speed_in_ghz"))
          .speedInMhz(rs.getFloat("speed_in_mhz"))
          .name(rs.getString("name"))
          .price(rs.getBigDecimal("price"))
          .sizeInNm(rs.getFloat("size_in_nm"))
          .build();
    }
    
    
  }

  @Override
  public Optional<Battery> fetchBattery(String batteryId) {
  //@formatter: off
    String sql =""
        + "SELECT * "
        + "FROM batteries "
        + "WHERE battery_id = :battery_id";
  //@formatter: on
    Map<String, Object> params = new HashMap<>();
    params.put("battery_id", batteryId);
    
    return Optional.ofNullable(jdbcTemplate.query(sql, params, new BatteryResultSetExtractor()));
  }
    
  class BatteryResultSetExtractor implements ResultSetExtractor<Battery>{

    @Override
    public Battery extractData(ResultSet rs) 
        throws SQLException, DataAccessException {
      rs.next();
      return Battery.builder()
          .batteryPK(rs.getLong("battery_pk"))
          .batteryId(rs.getString("battery_id"))
          .batteryCapacity(rs.getString("battery_capacity"))
          .manufacturer(rs.getString("manufacturer"))
          .price(rs.getBigDecimal("price"))
          .warrantyDays(rs.getInt("warranty_days"))
          .build();
    }
    
    
  }
  
  class SqlParams{
    String sql;
    MapSqlParameterSource source = new MapSqlParameterSource();
  }
  
  @Override
  public boolean deleteOption(String optionId) {
    String sql = """
            DELETE
            FROM options
            WHERE option_id = :option_id
            """;
    Map<String, Object> params = Map.of("option_id", optionId);
    return jdbcTemplate.update(sql, params)==1;
  }


  @Override
  public boolean updateOption(Option option) {
    
    String sql = """
            UPDATE options SET
            category=:category,
            manufacturer=:manufacturer,
            name=:name,
            price=:price
        WHERE option_pk=:option_pk
            """;
            
            
    // @formatter :off
    Map<String, Object> params = 
        Map.of("category", option.getCategory().toString(), 
        "manufacturer", option.getManufacturer(), 
            "name", option.getName(),
            "price", option.getPrice(),
            "option_pk", option.getOptionPK());
    // @formatter :on
        
    return jdbcTemplate.update(sql, params) == 1;
   
  }
  
  }


