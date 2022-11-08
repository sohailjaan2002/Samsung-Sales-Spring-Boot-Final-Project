package com.promineotechfinal.samsung.entity;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.Comparator;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Samsung implements Comparable<Samsung>{

private Long modelPK;
  
  private SamsungModel modelId;
  
  private String typeModel;
  
  private int storageInGB;
  
  private int screenSize;
  
  private BigDecimal launchPrice;
  
  @JsonIgnore
  public Long getModelPK() {
    return modelPK;
  }

  @Override
  public int compareTo(Samsung that) {
    //@formatter: off
    return Comparator
        .comparing(Samsung::getModelId)
        .thenComparing(Samsung::getTypeModel)
        .thenComparing(Samsung::getStorageInGB)
        .compare(this, that);
        
    //@formatter: on
  }

}
