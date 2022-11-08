package com.promineotechfinal.samsung.dao;

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
import org.springframework.stereotype.Component;
import com.promineotechfinal.samsung.entity.Customer;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultSamsungCustomerDao implements SamsungCustomerDao {

  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  @Override
  public List<Customer> fetchCustomer(Long customerPK, String customerId) {
    log.info("DAO: customerPK={} , customerId={}" , customerPK,customerId);
    
    //@formatter:off
    String sql = ""
        + "SELECT * "
        + "FROM customers "
        + "WHERE customer_pk= :customer_pk "
        + "AND customer_id= :customer_id";
    //@formatter:on
    
    Map<String,Object> params = new HashMap<>();
    params.put("customer_pk", customerPK);
    params.put("customer_id", customerId);
    
    return jdbcTemplate.query(sql, params, new RowMapper<>() {

      @Override
      public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        
        return Customer.builder()
            .customerPK(rs.getLong("customer_pk"))
            .customerId(rs.getString("customer_id"))
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .phone(rs.getString("phone"))
            .build();
      }});
  }

  @Override
  public Optional<Customer> creatCustomer(String customerId, String firstName, String lastName,
      String phone) {
    log.info("DAO: customerPK={}, customerId={}, firstName={}, lastName={}, phone={}", 
        customerId, firstName, lastName, phone);
    
    //@formatter:off
    
        String sql = ""
            + "INSERT INTO customers ("
            + "customer_pk, customer_id, first_name, last_name, phone"
            + ") VALUES ("
            +  "customer_pk, :customer_id, :first_name, :last_name, :phone)";
    //@formatter:on
    Map<String, Object> params = new HashMap<>();
    params.put("customer_id", customerId);
    params.put("first_name", firstName);
    params.put("last_name", lastName);
    params.put("phone", phone);
    
    jdbcTemplate.update(sql, params);
    
    return Optional.ofNullable(Customer.builder()
        .customerId(customerId)
        .firstName(firstName)
        .lastName(lastName)
        .phone(phone)
        .build());
        
  }

 
    
  //@formatter:off
    String sql = """
            UPDATE Customers SET
            first_name=:first_name,
            last_name=:last_name,
            phone=:phone
        WHERE customer_pk=:customer_pk
            """;

    @Override
    public boolean updateCustomer(Customer customer) {
      
    //@formatter:off
      String sql = """
              UPDATE Customers SET
              first_name=:first_name,
              last_name=:last_name,
              phone=:phone
          WHERE customer_pk=:customer_pk
              """;
      //@formatter:on
      
      Map<String,Object> params = 
          Map.of("first_name",customer.getFirstName(),
              "last_name",customer.getLastName(),
              "phone",customer.getPhone(),
              "customer_pk",customer.getCustomerPK());
      return jdbcTemplate.update(sql, params)==1;
    }

    @Override
    public boolean deleteCustomer(String customerId) {
      String sql = """
          DELETE
          FROM customers
          WHERE customer_id = :customer_id
          """;
      
      Map<String, Object> params = Map.of("customer_id", customerId);
          return jdbcTemplate.update(sql, params)==1;
    }

    
        
    
    
   
  
  }

 

  