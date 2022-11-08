package com.promineotechfinal.samsung.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotechfinal.samsung.entity.Color;
import com.promineotechfinal.samsung.service.SamsungColorService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultSamsungColorController implements SamsungColorController {

  @Autowired
  private SamsungColorService samsungColorService;
  @Override
  public Color updateColor(Color color) {
    log.info("Update color {}", color);
    return samsungColorService.updateColor(color);
  }

}
