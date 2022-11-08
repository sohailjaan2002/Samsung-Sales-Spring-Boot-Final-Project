package com.promineotechfinal.samsung.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.promineotechfinal.samsung.entity.Image;
import com.promineotechfinal.samsung.entity.Samsung;
import com.promineotechfinal.samsung.entity.SamsungModel;
import com.promineotechfinal.samsung.service.SamsungSalesService;
import lombok.extern.slf4j.Slf4j;

@RestController 
@Slf4j
public class DefaultSamsungSalesController implements SamsungSalesController {
  
  @Autowired
  private SamsungSalesService samsungSalesService;

  @Override
  public List<Samsung> fetchSamsung(SamsungModel model, String type) {
    log.info("model={}, type{}", model , type); // logging info (records HTTP request parameters to
                                                // the console)
    return samsungSalesService.fetchSamsung(model , type);
  }

  @Override
  public String uploadImage(MultipartFile file, Long modelPK) {
    log.debug("image={}, samsungPK={}", file, modelPK);
    String imageId = samsungSalesService.uploadImage(file,modelPK);
    String json = "{\"imageId\":\"" + imageId + "\"}";
    
    return json;
  }

  @Override
  public ResponseEntity<byte[]> retrieveImage(String imageId) {
    log.debug("Retrieving image with ID={}" , imageId);
    Image image = samsungSalesService.retrieveImage(imageId);
    
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", image.getMimeType().getMimeType());
    headers.add("Content-Length", Integer.toString(image.getData().length));
    
    return ResponseEntity.ok().headers(headers).body(image.getData());
  }

}
