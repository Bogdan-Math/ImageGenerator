package layer.repository;

import model.GalleryImage;

import java.util.List;

public interface GalleryImageRepository {
    List<GalleryImage> getAllThumbnails();
    void save(GalleryImage galleryImage);
}
