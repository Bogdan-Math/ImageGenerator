package layer.service;

import domain.InformationalImage;
import model.GalleryImage;

import java.util.List;

public interface GalleryImageService {

    List<GalleryImage> getAll();
    void save(String imageName, InformationalImage informationalImage);
}
