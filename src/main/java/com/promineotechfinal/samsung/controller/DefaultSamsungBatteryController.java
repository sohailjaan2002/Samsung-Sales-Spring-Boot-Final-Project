package com.promineotechfinal.samsung.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotechfinal.samsung.entity.Battery;
import com.promineotechfinal.samsung.service.SamsungBatteryService;
import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
public class DefaultSamsungBatteryController implements SamsungBatteryController {

  @Autowired
  private SamsungBatteryService samsungBatteryService;
  @Override
  public Battery updateBattery(Battery battery) {
    log.info("Update the battery {}", battery);
    return samsungBatteryService.updateBattery(battery);
  }

}
