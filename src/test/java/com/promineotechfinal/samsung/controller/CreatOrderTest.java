package com.promineotechfinal.samsung.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import com.promineotechfinal.samsung.entity.Order;
import com.promineotechfinal.samsung.entity.SamsungModel;
import lombok.Getter;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

@ActiveProfiles("test")

@Sql(scripts = {
    "classpath:flyway/migrations/Samsung_Schema.sql",
    "classpath:flyway/migrations/Samsung_Data.sql"}, 
    config = @SqlConfig(encoding = "utf-8"))

class CreatOrderTest {
  
  @Autowired
  @Getter
  private TestRestTemplate restTemplate;
  

  @LocalServerPort
  private int serverPort;

  @Autowired
  private JdbcTemplate jdbcTemplate;
  @Test
  void testCreateOrderReturnsSuccess201() {
 // Given: The order as a JSON
    
    String body = createOrderBody();
    String uri = String.format("http://localhost:%d/orders", serverPort);
    
    int numRowsOrders = JdbcTestUtils.countRowsInTable(jdbcTemplate, "orders");
    int numRowsOptions = JdbcTestUtils.countRowsInTable(jdbcTemplate, "order_options");
    
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    
    HttpEntity<String> bodyEntity = new HttpEntity<>(body, headers);
    
 // When: the order is sent
    ResponseEntity<Order>response = 
        restTemplate.exchange(uri, HttpMethod.POST, bodyEntity, Order.class);
    
 // Then: a 201 status is returned
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    
 // And: the returned order is correct
    assertThat(response.getBody()).isNotNull();
    
    Order order = response.getBody();
    assertThat(order.getCustomer().getCustomerId()).isEqualTo("AIDEN_SAMUEL");
    assertThat(order.getModel().getModelId()).isEqualTo(SamsungModel.GALAXY_S10);
    assertThat(order.getModel().getTypeModel()).isEqualTo("S10");
    assertThat(order.getModel().getStorageInGB()).isEqualTo(256);
    assertThat(order.getColor().getColorId()).isEqualTo("EXT_PRISM_BLACK");
    assertThat(order.getProcessor().getProcessorId()).isEqualTo("DUAL_CORE_2.4");
    assertThat(order.getBattery().getBatteryId()).isEqualTo("1000_MAH");
    assertThat(order.getOptions()).hasSize(6);
    
    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "orders"))
    .isEqualTo(numRowsOrders + 1);
    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "order_options"))
    .isEqualTo(numRowsOptions + 6);
    
  }

  /**
   * @return
   */
  protected String createOrderBody() {
    // TODO Auto-generated method stub
    return   "{\n"
        + "  \"customer\":\"AIDEN_SAMUEL\",\n"
        + "  \"model\":\"GALAXY_S10\",\n"
        + "  \"type\":\"S10\",\n"
        + "  \"storageInGB\":256,\n"
        + "  \"color\":\"EXT_PRISM_BLACK\",\n"
        + "  \"processor\":\"DUAL_CORE_2.4\",\n"
        + "  \"battery\":\"1000_MAH\",\n"
        + "  \"options\":[\n"
        + "    \"EXT_SAMSUNG_FHD\",\n"
        + "    \"EXT_SAMSUNG_QHD\",\n"
        + "    \"EXT_SAMSUNG_CHD\",\n"
        + "    \"EXT_SAMSUNG_DUAL_HD\",\n"
        + "    \"EXT_SAMSUNG_DYNAMIC\",\n"
        + "    \"EXT_SAMSUNG_WQHD\"\n"
        + "  ]\n"
        + "}";
         
  }

}
