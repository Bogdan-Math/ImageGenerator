package layer.repository;

import domain.InformationalImage;

import java.util.List;

public interface GalleryImageRepository {

    Integer getImagesCount();
    List<InformationalImage> getAll();
    void save(InformationalImage galleryImage);
    void deleteOldest();
}
