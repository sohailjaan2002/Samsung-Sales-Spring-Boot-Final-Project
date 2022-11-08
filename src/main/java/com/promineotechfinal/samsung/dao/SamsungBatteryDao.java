package com.promineotechfinal.samsung.dao;

import com.promineotechfinal.samsung.entity.Battery;

public interface SamsungBatteryDao {

  /**
   * @param battery
   * @return
   */
  boolean updateBattery(Battery battery);

}
