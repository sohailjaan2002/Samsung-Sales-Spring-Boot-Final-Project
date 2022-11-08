package com.promineotechfinal.samsung.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  
  private Long customerPK;
  private String customerId;
  private String firstName;
  private String lastName;
  private String phone;
  
  //@JsonIgnore
  //public Long getCustomerPK() {
   // return customerPK;
  //}
}
