package com.promineotechfinal.samsung.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.promineotechfinal.samsung.entity.Image;
import com.promineotechfinal.samsung.entity.Samsung;
import com.promineotechfinal.samsung.entity.SamsungModel;



public interface SamsungSalesService {

  /**
   * @param model
   * @param type
   * @return
   */
  
  List<Samsung> fetchSamsung(SamsungModel model, String type);

  /**
   * @param image
   * @param samsungPK
   * @return
   */
  String uploadImage(MultipartFile file, Long modelPK);

  /**
   * @param imageId
   * @return
   */
  Image retrieveImage(String imageId);

  

}
