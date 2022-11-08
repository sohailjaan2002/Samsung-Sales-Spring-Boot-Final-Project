package com.promineotechfinal.samsung.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotechfinal.samsung.entity.Samsung;
import com.promineotechfinal.samsung.entity.SamsungModel;





@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:flyway/migrations/Samsung_Schema.sql",
    "classpath:flyway/migrations/Samsung_Data.sql"}, config = @SqlConfig(encoding = "utf-8"))

class FetchSamsungTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @LocalServerPort
  private int serverPort;

  @Test
  void testThatSamsungsAreReturnedWhenAValidModelAndTypeAreSupplied() {
    // Given: a valid model, type and URI
    SamsungModel model = SamsungModel.GALAXY_S10;
    String type = "S10";
    String uri =
        String.format("http://localhost:%d/samsungs?model=%s&type=%s", serverPort, model, type);


    ResponseEntity<List<Samsung>> response =
        restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});

    // Then: a success (OK - 200) status code is returned.
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    // And: the actual list returned is the same as the expected list
    List<Samsung> actual = response.getBody();
    List<Samsung> expected = buildExpected();



    assertThat(actual).isEqualTo(expected);

  }

  /**
   * @return
   */
  protected List<Samsung> buildExpected() {
    List<Samsung> list = new LinkedList<>();
    //formatter:off
    list.add(Samsung.builder()
        .modelId(SamsungModel.GALAXY_S10)
        .typeModel("S10")
        .storageInGB(128)
        .screenSize(6)
        .launchPrice(new BigDecimal("899.00"))
        .build());
    
    list.add(Samsung.builder()
        .modelId(SamsungModel.GALAXY_S10)
        .typeModel("S10")
        .storageInGB(256)
        .screenSize(6)
        .launchPrice(new BigDecimal("999.00"))
        .build());
    //formatter:on
    
    Collections.sort(list);
    return list;
    
  }
}
