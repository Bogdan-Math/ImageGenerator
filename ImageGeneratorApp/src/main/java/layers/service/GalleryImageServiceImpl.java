package layers.service;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Image;
import layers.repository.GalleryImageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service("galleryImageService")
public class GalleryImageServiceImpl implements GalleryImageService {

    private GalleryImageRepository galleryImageRepository;

    @Override
    public List<Image> getAll() {
        return galleryImageRepository.getAll()
                .stream()
                .map(galleryImage -> new Image() {{
                    setSource(new StreamResource(() -> new ByteArrayInputStream(galleryImage.getBytes()),
                                                                                galleryImage.getName()));
                }})
                .collect(toList());
    }

    @Resource(name = "galleryImageRepository")
    public void setGalleryImageRepository(GalleryImageRepository galleryImageRepository) {
        this.galleryImageRepository = galleryImageRepository;
    }
}
