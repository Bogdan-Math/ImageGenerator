package layers.service;

import layers.repository.GalleryImageRepository;
import model.GalleryImage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("galleryImageService")
public class GalleryImageServiceImpl implements GalleryImageService {

    private GalleryImageRepository galleryImageRepository;

    @Override//TODO: add cache (maybe ehcache lib)
    public List<GalleryImage> getAll() {
        return galleryImageRepository.getAll();
    }

    @Override
    @Async
    public void save(GalleryImage galleryImage) {
        galleryImageRepository.save(galleryImage);
    }

    @Resource(name = "galleryImageRepository")
    public void setGalleryImageRepository(GalleryImageRepository galleryImageRepository) {
        this.galleryImageRepository = galleryImageRepository;
    }
}
