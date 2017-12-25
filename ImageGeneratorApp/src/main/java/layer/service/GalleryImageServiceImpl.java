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

    private static final int MAX_COUNT_IN_DB      = 100;
    private static final int MAX_COUNT_IN_GALLERY = 16;

    private GalleryImageRepository galleryImageRepository;

    @Override//TODO: add cache (maybe ehcache lib)
    public List<InformationalImage> getAllImages() {
        return galleryImageRepository.get(MAX_COUNT_IN_GALLERY);
    }

    @Override
    @Async
    public void save(InformationalImage informationalImage) {
        InformationalImage newGalleryImage = informationalImage.resizeTo(NEW_WIDTH, NEW_HEIGHT);
        if (galleryImageRepository.getTotalCount() < MAX_COUNT_IN_DB) {
            galleryImageRepository.add(newGalleryImage);
        } else {
            galleryImageRepository.replaceOldestBy(newGalleryImage);
        }
    }

    @Resource(name = "galleryImageRepository")
    public void setGalleryImageRepository(GalleryImageRepository galleryImageRepository) {
        this.galleryImageRepository = galleryImageRepository;
    }
}
