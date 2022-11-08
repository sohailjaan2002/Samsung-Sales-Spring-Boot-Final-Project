package com.promineotechfinal.samsung.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotechfinal.samsung.entity.Battery;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/batteries")
@OpenAPIDefinition(info = @Info(title = "Samsung Sales Service"), servers = {
    @Server(url = "http://localhost:8080", description = "Local server.")})

public interface SamsungBatteryController {

//@formatter:off
  @Operation(
      summary = "Updates a battery",
      description = "Update a battery using the required battery id, battery capacity, "
          + "manufacturer, price,  and warranty days",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A battery is updated!", 
              content = @Content(
                  mediaType = "application/json", 
              schema = @Schema(implementation = Battery.class))),
          @ApiResponse(
              responseCode = "400", 
              description = "The request parameters are invalid.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "404", 
              description = "Unable to update battery with the input criteria.", 
              content = @Content(
                  mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500", 
              description = "An unplanned error occurred.", 
              content = @Content(
                  mediaType = "application/json"))}
      
      
  )
  @PutMapping
  @ResponseStatus(code = HttpStatus.OK)
  Battery updateBattery(@RequestBody Battery battery);
}
