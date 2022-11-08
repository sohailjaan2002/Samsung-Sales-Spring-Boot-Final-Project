package com.promineotechfinal.samsung.dao;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import com.promineotechfinal.samsung.entity.Battery;
import com.promineotechfinal.samsung.entity.Color;
import com.promineotechfinal.samsung.entity.Customer;
import com.promineotechfinal.samsung.entity.Option;
import com.promineotechfinal.samsung.entity.Order;
import com.promineotechfinal.samsung.entity.Processor;
import com.promineotechfinal.samsung.entity.Samsung;
import com.promineotechfinal.samsung.entity.SamsungModel;


public interface SamsungOrderDao {

  
  Optional<Customer> fetchCustomer(String customer);
  
  Optional<Samsung> fetchModel(SamsungModel model,String type, int storageInGB);

  Optional<Color> fetchColor(String color);

  Optional<Processor> fetchProcessor(String processor);

  Optional<Battery> fetchBattery(String battery);

  Order saveOrder(Customer customer, Samsung samsung, Color color, Processor processor,
      Battery battery, BigDecimal price, List<Option> options);

  List<Option> fetchOptions(List<String> optionIds);

  /**
   * @param optionId
   * @return
   */
  boolean deleteOption(String optionId);

  /**
   * @param option
   * @return
   */
  boolean updateOption(Option option);

      
 
  

 
  

  
 
  
  

}
