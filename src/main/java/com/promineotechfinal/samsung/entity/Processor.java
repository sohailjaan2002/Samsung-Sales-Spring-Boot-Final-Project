package com.promineotechfinal.samsung.entity;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Processor {

  private Long processorPK;
  private String processorId;
  private Float sizeInNm;
  private String name;
  private ProcessorType processorType;
  private Float speedInGhz;
  private Float speedInMhz;
  private boolean hasStartStop;
  private String description;
  private BigDecimal price;
  
  @JsonIgnore
  public Long getProcessorPK() {
    return processorPK;
  }
  
}
