package com.promineotechfinal.samsung.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteTimeoutException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.promineotechfinal.samsung.dao.SamsungSalesDao;
import com.promineotechfinal.samsung.entity.Image;
import com.promineotechfinal.samsung.entity.ImageMimeType;
import com.promineotechfinal.samsung.entity.Samsung;
import com.promineotechfinal.samsung.entity.SamsungModel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DefaultSamsungSalesService implements SamsungSalesService {
  
  @Autowired 
  private SamsungSalesDao samsungSalesDao;

  @Override
  public List<Samsung> fetchSamsung(SamsungModel model, String type) {
    log.info("The fetchSamsung method was called with model={} and trim={}" , model , type);
    
    List<Samsung> samsungs = samsungSalesDao.fetchSamsung(model , type);
    
    Collections.sort(samsungs);
    return samsungs ;
  }

  @Transactional
  @Override
  public String uploadImage(MultipartFile file, Long modelPK) {
    String imageId = UUID.randomUUID().toString();
    
    try(InputStream inputStream = file.getInputStream()){
      BufferedImage bufferedImage = ImageIO.read(inputStream);
      //@formatter:off
      Image image = Image.builder()
          .modelFK(modelPK)
          .imageId(imageId)
          .width(bufferedImage.getWidth())
          .height(bufferedImage.getHeight())
          .mimeType(ImageMimeType.IMAGE_JPEG)
          .name(file.getOriginalFilename())
          .data(toByteArray(bufferedImage,"jpeg"))
          .build();
      
      //@formatter:on
      
       samsungSalesDao.saveImage(image);
       
       return imageId;
      
    } catch (IOException e) {
      throw new UncheckedIOException(e);
      
    }
    
    
  }

  /**
   * @param bufferedImage
   * @param string
   * @return
   */
  private byte[] toByteArray(BufferedImage bufferedImage, String rederType) {
    
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      if(!ImageIO.write(bufferedImage, rederType, baos)) {
        throw new RuntimeException("could not write to image buffer");
      }
      return baos.toByteArray();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @Transactional(readOnly = true)
  @Override
  public Image retrieveImage(String imageId) {
    
    return samsungSalesDao.retrieveImage(imageId)
        .orElseThrow(() -> new NoSuchElementException("Could not find image with Id=" + imageId));
  }

  
  

}
