package layer.repository;

import domain.InformationalImage;

import java.util.List;

public interface GalleryImageRepository {

    Integer getImagesCount();
    List<InformationalImage> getAllImages();
    void add(InformationalImage newGalleryImage);
    void replaceOldestBy(InformationalImage newGalleryImage);
}
