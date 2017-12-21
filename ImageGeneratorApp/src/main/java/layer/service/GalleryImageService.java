package layer.service;

import domain.InformationalImage;
import model.GalleryImage;

import java.util.List;

public interface GalleryImageService {

    List<InformationalImage> getAll();
    void save(InformationalImage informationalImage);
}
