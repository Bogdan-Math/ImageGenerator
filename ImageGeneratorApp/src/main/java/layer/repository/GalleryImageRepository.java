package layer.repository;

import domain.InformationalImage;
import model.GalleryImage;

import java.util.List;

public interface GalleryImageRepository {

    List<InformationalImage> getAll();
    void save(InformationalImage galleryImage);
}
