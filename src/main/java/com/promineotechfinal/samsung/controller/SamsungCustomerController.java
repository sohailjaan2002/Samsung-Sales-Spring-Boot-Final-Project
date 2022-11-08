package com.promineotechfinal.samsung.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.promineotechfinal.samsung.entity.Customer;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/Customers")
@OpenAPIDefinition(info = @Info(title = "Samsung Sales Service"), servers = {
    @Server(url = "http://localhost:8080", description = "Local server.")})


public interface SamsungCustomerController {

//@formatter:off
 @Operation(
     summary = "Returns a List of Customers",
     description = "Returns a list of Customers given a required customerPK and/or optional customerPK",
     responses = {
         @ApiResponse(
             responseCode = "200", 
             description = "A list of customers was returned", 
             content = @Content(mediaType = "application/json", 
             schema = @Schema(implementation = Customer.class))),
         @ApiResponse(
             responseCode = "400",
             description = "The Request parameters are invalid",
             content = @Content(mediaType = "application/json")
         ),
         @ApiResponse(
             responseCode = "404",
             description = "No Customer were found with the input criteria",
             content = @Content(mediaType = "application/json")),
         @ApiResponse(
             responseCode = "500",
             description = "An unplanned error occured",
             content = @Content(mediaType = "application/json"))
         },

     parameters = {
         @Parameter(
             name = "customerPK",
             allowEmptyValue = false,
             required = false,
             description = "The customerPK (i.e. '12')"),
         @Parameter(
             name = "customerId",
             allowEmptyValue = false,
             required = false,
             description = "The customerId (i.e. 'DAVID_WYATT')")
     }
  )
 //Get Method 
 @GetMapping
 @ResponseStatus(code = HttpStatus.OK)
 List<Customer> fetchCustomer
 (@RequestParam(required=false) 
 Long customerPK, 
 @RequestParam(required=false) 
 String customerId);
 
 // Post (Creat)***********
 
 @Operation(
     summary = "Creates a customer",
     description = "Create a customer using the required customer id, first name, "
         + "last name, and phone number.",
     responses = {
         @ApiResponse(
             responseCode = "200", 
             description = "A customer is created!", 
             content = @Content(
                 mediaType = "application/json", 
             schema = @Schema(implementation = Customer.class))),
         @ApiResponse(
             responseCode = "400", 
             description = "The request parameters are invalid.", 
             content = @Content(
                 mediaType = "application/json")),
         @ApiResponse(
             responseCode = "404", 
             description = "Unable to create customer with the input criteria.", 
             content = @Content(
                 mediaType = "application/json")),
         @ApiResponse(
             responseCode = "500", 
             description = "An unplanned error occurred.", 
             content = @Content(
                 mediaType = "application/json"))
     },
     parameters = {
         @Parameter(
             name = "customerId", 
             allowEmptyValue = false, 
             required = false, 
             description = "The customer id (i.e., 'AIDEN_SAMUEL')"), 
         @Parameter(
             name = "firstName",
             allowEmptyValue = false,
             required = false,
             description = "The first name (i.e., 'Samuel')"),
         @Parameter(
             name = "lastName",
             allowEmptyValue = false,
             required = false,
             description = "The last name (i.e., 'Aiden')"),
         @Parameter(
             name = "phone",
             allowEmptyValue = false,
             required = false,
             description = "The phone number (i.e., '755.223.5969')")
     }
    )
 
 // Post method 
 @PostMapping
 @ResponseStatus(code = HttpStatus.CREATED)
 Optional<Customer> creatCustomer(
     @RequestParam(required = false) 
     String customerId, 
     @RequestParam(required = false) 
     String firstName, 
     @RequestParam(required = false) 
     String lastName, 
     @RequestParam(required = false) 
     String phone);
 
 //put update************
 @Operation(
     summary = "Updates a customer",
     description = "Update a customer using the required customer id, first name, "
         + "last name, and phone number.",
     responses = {
         @ApiResponse(
             responseCode = "200", 
             description = "A customer is updated!", 
             content = @Content(
                 mediaType = "application/json", 
             schema = @Schema(implementation = Customer.class))),
         @ApiResponse(
             responseCode = "400", 
             description = "The request parameters are invalid.", 
             content = @Content(
                 mediaType = "application/json")),
         @ApiResponse(
             responseCode = "404", 
             description = "Unable to update customer with the input criteria.", 
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
 Customer updateCustomer(@RequestBody Customer customer);
 
 @Operation(
     summary = "Delete a customer",
     description = "Delete a customer using the required customer id",
     responses = {
         @ApiResponse(
             responseCode = "200", 
             description = "A customer is updated!", 
             content = @Content(
                 mediaType = "application/json", 
             schema = @Schema(implementation = Customer.class))),
         @ApiResponse(
             responseCode = "400", 
             description = "The request parameters are invalid.", 
             content = @Content(
                 mediaType = "application/json")),
         @ApiResponse(
             responseCode = "404", 
             description = "Unable to update customer with the input criteria.", 
             content = @Content(
                 mediaType = "application/json")),
         @ApiResponse(
             responseCode = "500", 
             description = "An unplanned error occurred.", 
             content = @Content(
                 mediaType = "application/json"))}
     
     
 )
 @DeleteMapping
 @ResponseStatus(code=HttpStatus.OK)
 Map<String,Object> deleteCustomer(@RequestParam String customerId);
 
 
}

