package layers.service;

import model.GalleryImage;

import java.util.List;

public interface GalleryImageService {

    List<GalleryImage> getAll();
    void save(GalleryImage galleryImage);
}
