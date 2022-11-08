package com.promineotechfinal.samsung.service;

import javax.validation.Valid;
import com.promineotechfinal.samsung.entity.Option;
import com.promineotechfinal.samsung.entity.Order;
import com.promineotechfinal.samsung.entity.OrderRequest;

public interface SamsungOrderService {

  
  Order creatOrder(@Valid OrderRequest orderRequest);

  /**
   * @param optionId
   */
  void deleteOption(String optionId);

  /**
   * @param option
   * @return
   */
  Option updateOption(Option option);

}
