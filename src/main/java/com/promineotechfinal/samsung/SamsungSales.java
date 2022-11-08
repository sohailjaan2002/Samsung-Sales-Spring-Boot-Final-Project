package com.promineotechfinal.samsung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.promineotechfinal.ComponentScanMarker;

@SpringBootApplication(scanBasePackageClasses = {ComponentScanMarker.class})
public class SamsungSales {

  public static void main(String[] args) {
    SpringApplication.run(SamsungSales.class, args);

  }

}
