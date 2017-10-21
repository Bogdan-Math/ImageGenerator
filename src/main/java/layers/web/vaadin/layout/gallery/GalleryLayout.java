package layers.web.vaadin.layout.gallery;

import com.github.lotsabackscatter.blueimp.gallery.Gallery;
import com.github.lotsabackscatter.blueimp.gallery.Image;
import com.github.lotsabackscatter.blueimp.gallery.Image.Builder;
import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import utility.system.ResourceReader;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static utility.core.PatternType.FLAGS;

@SpringComponent
@Scope("session")
public class GalleryLayout extends VerticalLayout {

    @Autowired
    private ResourceReader resourceReader;

    private List<com.vaadin.ui.Image> layoutImages;
    private Gallery gallery;

    @PostConstruct
    public void postConstruct() {
        HorizontalLayout allImagesLayout = new HorizontalLayout();
        allImagesLayout.setSizeFull();

        layoutImages = resourceReader.readFiles(FLAGS.getLocation())
                                     .stream()
                                     .map(imgFile -> new com.vaadin.ui.Image() {{
                                         setSource(new FileResource(imgFile));
                                         setStyleName("gallery-image");
                                     }})
                                     .collect(toList());

        layoutImages.forEach(allImagesLayout::addComponent);

        gallery = new Gallery();
        layoutImages.forEach(img -> img.addClickListener(event -> {
            List<Image> galleryImages = layoutImages.stream()
                                                    .map(this::buildGalleryImage)
                                                    .collect(toList());

            gallery.showGallery(buildGalleryImage(img), galleryImages.toArray(new Image[galleryImages.size()]));
        }));

        allImagesLayout.addComponent(gallery);
        addComponent(allImagesLayout);
    }

    private Image buildGalleryImage(com.vaadin.ui.Image layoutImage) {
        return new Builder().href(getResourceURL(layoutImage, layoutImage.getCaption()))
                            .thumbnail(getResourceURL(layoutImage, layoutImage.getCaption()))
                            .build();
    }

    private String getResourceURL(AbstractClientConnector connector, String fileName) {
        String domain       = UI.getCurrent().getPage().getLocation().getSchemeSpecificPart();
        Integer uiId        = connector.getUI().getUIId();
        String connectorId  = connector.getConnectorId();
        return domain + "APP/connector/" + uiId + "/" + connectorId + "/source/" + fileName;
    }
}
