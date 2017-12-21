package layer.web.vaadin.layout.gallery;

import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import layer.service.GalleryImageService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

import static java.lang.Integer.min;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.iterate;

@SpringComponent
@Scope("session")
public class GalleryLayout extends VerticalLayout implements GalleryLayoutBuilder {

    private static final String STREAM_RESOURCE_FILENAME = "";
    private static final String GALLERY_STYLE            = "gallery-image";
    private static final Integer COLUMNS_COUNT           = 4;

    private GalleryImageService galleryImageService;

    @PostConstruct
    public void postConstruct() {
        setSizeFull();
    }

    @Override
    public void buildGallery() {
        removeAllComponents();
        pagedGallery(galleryImageService.getAllImages()
                .stream()
                .map(galleryImage -> new StreamResource(galleryImage::asStream, STREAM_RESOURCE_FILENAME))
                .map(resource -> new Image() {{ setSource(resource); }})
                .peek(image -> image.setStyleName(GALLERY_STYLE))
                .collect(toList())).forEach(list -> this.addComponent(addNewLine(list)));
    }

    private List<List<Image>> pagedGallery(List<Image> list) {
        return iterate(0, i -> i + COLUMNS_COUNT)
                .limit((list.size() + COLUMNS_COUNT - 1) / COLUMNS_COUNT)
                .boxed()
                .map(i -> list.subList(i, min(i + COLUMNS_COUNT, list.size())))
                .collect(toList());
    }

    private HorizontalLayout addNewLine(List<Image> images) {
        HorizontalLayout newLine = new HorizontalLayout();
        images.forEach(image -> {
            newLine.addComponent(image);
            newLine.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
        });
        newLine.setSizeFull();
        return newLine;
    }

    @Resource(name = "galleryImageService")
    public void setGalleryImageService(GalleryImageService galleryImageService) {
        this.galleryImageService = galleryImageService;
    }
}
