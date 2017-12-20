package layer.service;

import domain.InformationalImage;
import model.GalleryImage;

import java.util.List;

public interface GalleryImageService {

    List<GalleryImage> getAllThumbnails();
    void save(String imageName, InformationalImage informationalImage);
}
