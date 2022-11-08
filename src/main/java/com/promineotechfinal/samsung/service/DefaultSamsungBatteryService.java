package com.promineotechfinal.samsung.service;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promineotechfinal.samsung.dao.SamsungBatteryDao;
import com.promineotechfinal.samsung.entity.Battery;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultSamsungBatteryService implements SamsungBatteryService {

  @Autowired
  private SamsungBatteryDao samsungBatteryDao;
  
  @Override
  public Battery updateBattery(Battery battery) {
    if(!samsungBatteryDao.updateBattery(battery)) {
      throw new NoSuchElementException("Customer with Id= " + battery.getBatteryId() + "does not exist");
    }
    return battery;
  }

}
