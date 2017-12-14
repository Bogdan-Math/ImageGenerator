package layer.repository;

import model.GalleryImage;

import java.util.List;

public interface GalleryImageRepository {
    List<GalleryImage> getAll();
    void save(GalleryImage galleryImage);
}
