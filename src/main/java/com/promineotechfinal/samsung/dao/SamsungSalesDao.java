package com.promineotechfinal.samsung.dao;

import java.util.List;
import java.util.Optional;
import com.promineotechfinal.samsung.entity.Image;
import com.promineotechfinal.samsung.entity.Samsung;
import com.promineotechfinal.samsung.entity.SamsungModel;

public interface SamsungSalesDao {

  /**
   * @param model
   * @param type
   * @return
   */
  List<Samsung> fetchSamsung(SamsungModel model, String type);

  /**
   * @param image
   */
  void saveImage(Image image);

  /**
   * @param imageId
   * @return
   */
  Optional<Image> retrieveImage(String imageId);

  

}
