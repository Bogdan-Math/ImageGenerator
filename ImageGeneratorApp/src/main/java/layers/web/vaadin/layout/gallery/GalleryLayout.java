package layers.web.vaadin.layout.gallery;

import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import system.ResourceReader;

import javax.annotation.PostConstruct;
import java.util.List;

import static domain.PatternType.COMMONS;
import static java.lang.Integer.min;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.iterate;

@SpringComponent
@Scope("session")
public class GalleryLayout extends VerticalLayout {

    private static final String GALLERY_STYLE = "gallery-image";

    @Autowired
    private ResourceReader resourceReader;

    @PostConstruct
    public void postConstruct() {
        VerticalLayout allImagesLayout = new VerticalLayout();
        allImagesLayout.setSizeFull();

        List<Image> images = resourceReader.readFiles(COMMONS.getLocation())
                .stream()
                .map(imgFile -> new Image() {{
                    setSource(new FileResource(imgFile));
                    setStyleName(GALLERY_STYLE);
                }}).collect(toList());

        List<List<Image>> pagedGallery = pagedGallery(images, 3);
        pagedGallery.forEach(list -> allImagesLayout.addComponent(addNewLine(list)));

        addComponent(allImagesLayout);
    }

    private List<List<Image>> pagedGallery(List<Image> list, int pageSize) {
        return iterate(0, i -> i + pageSize).limit((list.size() + pageSize - 1) / pageSize)
                                                  .boxed()
                                                  .map(i -> list.subList(i, min(i + pageSize, list.size())))
                                                  .collect(toList());
    }

    private HorizontalLayout addNewLine(List<Image> images) {
        HorizontalLayout newLine = new HorizontalLayout();
        images.forEach(image -> {
            newLine.addComponent(image);
            newLine.setComponentAlignment(image, Alignment.TOP_CENTER);
        });
        newLine.setSizeFull();
        return newLine;
    }
}
