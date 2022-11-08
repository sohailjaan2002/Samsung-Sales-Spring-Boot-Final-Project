package com.promineotechfinal.samsung.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotechfinal.samsung.dao.SamsungCustomerDao;
import com.promineotechfinal.samsung.entity.Customer;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultSamsungCustomerService implements SamsungCustomerService {

  @Autowired
  private SamsungCustomerDao samsungCustomerDao;
  @Override
  public List<Customer> fetchCustomer(Long customerPK, String customerId) {
    log.info("The fetchCustomer method was called with customerPK={} and customerId" , 
        customerPK, customerId);
    return samsungCustomerDao.fetchCustomer(customerPK,customerId);
    
  }
  @Override
  public Optional<Customer> creatCustomer(String customerId, String firstName, String lastName,
      String phone) {
    log.info("The creatCustomer method was called with customerPK={}, customerId={}," + "firstName={}, lastName={},phone={}",
        customerId ,firstName,lastName,phone);
    return samsungCustomerDao.creatCustomer(customerId , firstName, lastName, phone);
  }
  @Override
  public Customer updateCustomer(Customer customer) {
    if(!samsungCustomerDao.updateCustomer(customer)) {
      throw new NoSuchElementException("Customer with Id= " + customer.getCustomerId() + "does not exist");
    }
    return customer;
  }
  
  @Transactional(readOnly=false)
  @Override
  public void deleteCustomer(String customerId) {
    if(!samsungCustomerDao.deleteCustomer(customerId)) {
      throw new NoSuchElementException("Customer with ID= " + customerId + " does not exist.");
    }
    
  }
  
  
    
  }
  
  


