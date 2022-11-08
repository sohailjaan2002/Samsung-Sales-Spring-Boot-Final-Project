package com.promineotechfinal.samsung.entity;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class OrderRequest {
  @NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String customer;
  
  @NotNull
  private SamsungModel model;
  
  @NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String type;
  
  @Positive
  @Min(64)
  @Max(512)
  private int storageInGB;
  
  @NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*" )
  private String color;
  
  @NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s.]*" )
  private String processor;
  
  @NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*" )
  private String battery;
  
  
  private List<@NotNull @Length(max = 30) @Pattern(
      regexp = "[A-Z0-9_]*")String> options;
  
  
}
