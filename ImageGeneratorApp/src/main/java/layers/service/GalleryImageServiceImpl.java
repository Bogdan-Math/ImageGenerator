package layers.service;

import layers.repository.GalleryImageRepository;
import model.GalleryImage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("galleryImageService")
public class GalleryImageServiceImpl implements GalleryImageService {

    private GalleryImageRepository galleryImageRepository;

    @Override//TODO: add cache
    public List<GalleryImage> getAll() {
        return galleryImageRepository.getAll();
    }

    @Resource(name = "galleryImageRepository")
    public void setGalleryImageRepository(GalleryImageRepository galleryImageRepository) {
        this.galleryImageRepository = galleryImageRepository;
    }
}
