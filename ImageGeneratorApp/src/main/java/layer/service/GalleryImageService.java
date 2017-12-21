package layer.service;

import domain.InformationalImage;

import java.util.List;

public interface GalleryImageService {

    List<InformationalImage> getAllImages();
    void save(InformationalImage informationalImage);
}
