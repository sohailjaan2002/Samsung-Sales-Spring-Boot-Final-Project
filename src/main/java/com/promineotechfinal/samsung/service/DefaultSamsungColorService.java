package com.promineotechfinal.samsung.service;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.promineotechfinal.samsung.dao.SamsungColorDao;
import com.promineotechfinal.samsung.entity.Color;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultSamsungColorService implements SamsungColorService {

  @Autowired
  private SamsungColorDao samsungColorDao;
  
  @Override
  public Color updateColor(Color color) {
    if(!samsungColorDao.updateColor(color)) {
      throw new NoSuchElementException("Color with ID=" + color.getColorId() + "does not exist");
    }
    return color;
  }

}
