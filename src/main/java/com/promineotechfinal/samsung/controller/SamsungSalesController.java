package com.promineotechfinal.samsung.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import com.promineotechfinal.samsung.entity.Samsung;
import com.promineotechfinal.samsung.entity.SamsungModel;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/Samsungs")
@OpenAPIDefinition(info = @Info(title = "Samsung Sales Service"), servers = {
    @Server(url = "http://localhost:8080", description = "Local server.")})

public interface SamsungSalesController {

  
  // @formatter:off
  @Operation(
      summary = "Returns a List of Samsungs",
      description = "Returns a list of Samsungs given an optional model and/or type",
      responses = {
          @ApiResponse(
              responseCode = "200", 
              description = "A list of Samsungs was returned", 
              content = @Content(mediaType = "application/json", 
              schema = @Schema(implementation = Samsung.class))),
          @ApiResponse(
              responseCode = "400",
              description = "The Request parameters are invalid",
              content = @Content(mediaType = "application/json")
          ),
          @ApiResponse(
              responseCode = "404",
              description = "No Samsung were found with the input criteria",
              content = @Content(mediaType = "application/json")),
          @ApiResponse(
              responseCode = "500",
              description = "An unplanned error occured",
              content = @Content(mediaType = "application/json"))
          },
      
      parameters = {
          @Parameter(
              name = "model",
              allowEmptyValue = false,
              required = false,
              description = "The model name (i.e. 'GALAXY_S10')"),
          @Parameter(
              name = "type",
              allowEmptyValue = false,
              required = false,
              description = "The type name (i.e. 'S10')")
      }
   )
//@formatter:on

 @GetMapping
 @ResponseStatus(code = HttpStatus.OK)
 List<Samsung> fetchSamsung(
     @RequestParam
     SamsungModel model, 
     @RequestParam
     String type);
  
  @PostMapping("/{modelPK}/image")
  @ResponseStatus(code = HttpStatus.CREATED)
  String uploadImage(@RequestParam("image") MultipartFile image, 
      @PathVariable Long modelPK);
  
  @GetMapping("/image/{imageId}")
  ResponseEntity<byte[]> retrieveImage(@PathVariable String imageId);
}      
