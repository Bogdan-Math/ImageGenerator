package layer.service;

import domain.InformationalImage;
import layer.repository.GalleryImageRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("galleryImageService")
public class GalleryImageServiceImpl implements GalleryImageService {

    private static final int NEW_WIDTH  = 300;
    private static final int NEW_HEIGHT = 300;

    private GalleryImageRepository galleryImageRepository;

    @Override//TODO: add cache (maybe ehcache lib) and rename it
    public List<InformationalImage> getAll() {
        return galleryImageRepository.getAll();
    }

    @Override
    @Async
    public void save(InformationalImage informationalImage) {
        galleryImageRepository.save(informationalImage.resizeTo(NEW_WIDTH, NEW_HEIGHT));
    }

    @Resource(name = "galleryImageRepository")
    public void setGalleryImageRepository(GalleryImageRepository galleryImageRepository) {
        this.galleryImageRepository = galleryImageRepository;
    }
}
