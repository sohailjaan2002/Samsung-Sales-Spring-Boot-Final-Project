package com.promineotechfinal.samsung.controller;

import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotechfinal.samsung.entity.Option;
import com.promineotechfinal.samsung.entity.Order;
import com.promineotechfinal.samsung.entity.OrderRequest;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/orders")
@OpenAPIDefinition(info = @Info(title = "Samsung smart phones Order Service"),
servers = {@Server(url = "http://localhost:8080", description = "Local server.")})

public interface SamsungOrderController {

  
//@formatter:off
  @Operation(
      summary = "Create an order for a Samsung",
      description = "Returns the created Samsung ",
      responses = { 
          @ApiResponse(
              responseCode = "201", 
              description = "The created Samsung is returned", 
              content = @Content(
              mediaType = "application/json", 
              schema = @Schema(implementation = Order.class))),
          @ApiResponse(
              responseCode = "400",
              description ="The request parameters are invalid",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404",
              description ="A Samsung Components was not found with the given criterea",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description ="An unplanned error occurred",
              content = @Content(mediaType = "application/json")),
},
          parameters = {
              @Parameter(
                  name = "orderRequest", 
                  required = true, 
                  description = "The order as JSON")
          })
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
 Order createOrder(@Valid @RequestBody OrderRequest orderRequest); 
  
  @DeleteMapping("/option")
  Map<String, Object> deleteOption(@RequestParam String optionId);
  
  @PutMapping("/option")
  Option updateOption(@RequestBody Option option);
  
  
}
