package layers.service;

import domain.InformationalImage;
import layers.repository.GalleryImageRepository;
import model.GalleryImage;
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
    public List<GalleryImage> getAll() {
        return galleryImageRepository.getAll();
    }

    @Override
    @Async
    public void save(String imageName, InformationalImage informationalImage) {
        galleryImageRepository.save(convert(imageName, informationalImage));
    }

    private GalleryImage convert(String imageName, InformationalImage informationalImage) {
        return new GalleryImage() {{
            setName(imageName);
            setFullImage(informationalImage.asBytes());
            setThumbnailImage(informationalImage.resizeTo(NEW_WIDTH, NEW_HEIGHT)//TODO: move resize process to scheduler
                                                .asBytes());
        }};
    }

    @Resource(name = "galleryImageRepository")
    public void setGalleryImageRepository(GalleryImageRepository galleryImageRepository) {
        this.galleryImageRepository = galleryImageRepository;
    }
}
