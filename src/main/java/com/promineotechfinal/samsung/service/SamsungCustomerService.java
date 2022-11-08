package com.promineotechfinal.samsung.service;

import java.util.List;
import java.util.Optional;
import com.promineotechfinal.samsung.entity.Customer;

public interface SamsungCustomerService {

  /**
   * @param customerPK
   * @param customerId
   * @return
   */
  List<Customer> fetchCustomer(Long customerPK, String customerId);

  /**
   * @param customerId
   * @param firstName
   * @param lastName
   * @param phone
   * @return
   */
  Optional<Customer> creatCustomer(String customerId, String firstName, String lastName,
      String phone);

  /**
   * @param customer
   * @return
   */
  Customer updateCustomer(Customer customer);

  /**
   * @param customerId
   */
  void deleteCustomer(String customerId);

  
}