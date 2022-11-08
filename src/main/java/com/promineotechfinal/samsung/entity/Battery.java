package com.promineotechfinal.samsung.entity;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Battery {

  private Long batteryPK;
  private String batteryId;
  private String batteryCapacity;
  private String manufacturer;
  private BigDecimal price;
  private int warrantyDays;
  
  @JsonIgnore
  public Long getBatteryPK() {
    return batteryPK;
  }
}
