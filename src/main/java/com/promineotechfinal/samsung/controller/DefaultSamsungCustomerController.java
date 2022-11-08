package com.promineotechfinal.samsung.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotechfinal.samsung.entity.Customer;
import com.promineotechfinal.samsung.service.SamsungCustomerService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultSamsungCustomerController implements SamsungCustomerController {

  
  @Autowired
  private SamsungCustomerService samsungCustomerService;
  
  @Override
  public List<Customer> fetchCustomer(Long customerPK, String customerId) {
    log.info("customerPK={},customerId={}",customerPK,customerId);
    return samsungCustomerService.fetchCustomer(customerPK,customerId);
  }

  @Override
  public Optional<Customer> creatCustomer(String customerId, String firstName, String lastName,
      String phone) {
    log.info("customerId={}, firstName={}, lastName={}, phone={}", customerId , 
        firstName, lastName, phone);
    
    return samsungCustomerService.creatCustomer(customerId, firstName, lastName, phone);
  }

  @Override
  public Customer updateCustomer(Customer customer) {
    log.info("Update customer {}", customer);
    return samsungCustomerService.updateCustomer(customer);
  }

  @Override
  public Map<String, Object> deleteCustomer(String customerId) {
    samsungCustomerService.deleteCustomer(customerId);
    return Map.of("Message", "Customer with Id = " + customerId + " successfully deleted.");
  }

  
  
  

}
