package com.promineotechfinal.samsung.dao;

import java.util.List;
import java.util.Optional;
import com.promineotechfinal.samsung.entity.Customer;

public interface SamsungCustomerDao {

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
  boolean updateCustomer(Customer customer);

  /**
   * @param customerId
   * @return
   */
  boolean deleteCustomer(String customerId);

  

 
  
  
}
