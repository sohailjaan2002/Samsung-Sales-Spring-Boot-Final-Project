package com.promineotechfinal.samsung.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotechfinal.samsung.dao.SamsungOrderDao;
import com.promineotechfinal.samsung.entity.Battery;
import com.promineotechfinal.samsung.entity.Color;
import com.promineotechfinal.samsung.entity.Customer;
import com.promineotechfinal.samsung.entity.Option;
import com.promineotechfinal.samsung.entity.Order;
import com.promineotechfinal.samsung.entity.OrderRequest;
import com.promineotechfinal.samsung.entity.Processor;
import com.promineotechfinal.samsung.entity.Samsung;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultSamsungOrderService implements SamsungOrderService {
  
  @Autowired
  private SamsungOrderDao samsungOrderDao;

  @Transactional
  @Override
  public Order creatOrder(@Valid OrderRequest orderRequest) {
    
    Customer customer = getCustomer(orderRequest);
    Samsung samsung = getModel(orderRequest);
    Color color = getColor(orderRequest);
    Processor processor = getProcessor(orderRequest);
    Battery battery = getBattery(orderRequest);
    List<Option> options = getOption(orderRequest);
    
    
     BigDecimal price = samsung.getLaunchPrice().add(color.getPrice())
         .add(processor.getPrice()).add(battery.getPrice()); 
     
   for(Option option : options) {
     price = price.add(option.getPrice());
   }
    
    
    return samsungOrderDao.saveOrder(customer , samsung , color , processor, battery , price, options );
    
  }

  /**
   * @param orderRequest
   * @return
   */
  private List<Option> getOption(OrderRequest orderRequest) {
    return samsungOrderDao.fetchOptions(orderRequest.getOptions());
  }

  protected Battery getBattery(OrderRequest orderRequest) {
    return samsungOrderDao.fetchBattery(orderRequest.getBattery())
        .orElseThrow(() -> new NoSuchElementException("Battery with ID="
        + orderRequest.getBattery() + " was not found "));
  }

  protected Processor getProcessor(OrderRequest orderRequest) {
    return samsungOrderDao.fetchProcessor(orderRequest.getProcessor())
        .orElseThrow(() -> new NoSuchElementException("Processor with ID="
        + orderRequest.getProcessor() + " was not found "));
  }

  protected Color getColor(OrderRequest orderRequest) {
    return samsungOrderDao.fetchColor(orderRequest.getColor())
        .orElseThrow(() -> new NoSuchElementException("Color with ID="
        + orderRequest.getColor() + " was not found "));
  }

  protected Samsung getModel(OrderRequest orderRequest) {
    return samsungOrderDao.fetchModel(orderRequest.getModel(),
        orderRequest.getType(),orderRequest.getStorageInGB())
        .orElseThrow(() -> new NoSuchElementException("Model with ID =" 
        + orderRequest.getModel() +",type"+ orderRequest.getType()+ orderRequest.getStorageInGB()+"was not found"));
  }

  protected Customer getCustomer(OrderRequest orderRequest) {
    return samsungOrderDao.fetchCustomer(orderRequest.getCustomer())
        .orElseThrow(() -> new NoSuchElementException("Customer with ID="
    + orderRequest.getCustomer() + " was not found "));
  }

  @Transactional(readOnly = false)
  @Override
  public void deleteOption(String optionId) {
   if(!samsungOrderDao.deleteOption(optionId)) {
     throw new NoSuchElementException("Option with ID=" + optionId + "does not exist");
   }
    
  }

  @Override
  public Option updateOption(Option option) {
    // TODO Auto-generated method stub
    if(!samsungOrderDao.updateOption(option)) {
      throw new NoSuchElementException("Option with ID=" + option.getOptionId() + "does not exist");
    }
    return option;
  }

}
