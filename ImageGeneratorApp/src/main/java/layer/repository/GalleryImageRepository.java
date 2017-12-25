package layer.repository;

import domain.InformationalImage;

import java.util.List;

public interface GalleryImageRepository {

    Integer getTotalCount();
    List<InformationalImage> getAllImages();
    List<InformationalImage> get(Integer count);
    void add(InformationalImage newGalleryImage);
    void replaceOldestBy(InformationalImage newGalleryImage);
}
