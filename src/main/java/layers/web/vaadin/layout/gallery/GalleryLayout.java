package layers.web.vaadin.layout.gallery;

import com.github.lotsabackscatter.blueimp.gallery.Gallery;
import com.github.lotsabackscatter.blueimp.gallery.Image.Builder;
import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import utility.core.PatternType;
import utility.system.ResourceReader;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@SpringComponent
@Scope("session")
public class GalleryLayout extends VerticalLayout {

    @Autowired
    private ResourceReader resourceReader;
    private List<Image> images;
    private Gallery gallery;

    @PostConstruct
    public void postConstruct() {
        HorizontalLayout allImagesLayout = new HorizontalLayout();
        allImagesLayout.setSizeFull();

        images = resourceReader.readFiles(PatternType.PLAINS.getLocation())
                               .stream()
                               .map(imgFile -> new Image(imgFile.getName(), new FileResource(imgFile)))
                               .collect(toList());

        images.forEach(allImagesLayout::addComponent);

        gallery = new Gallery();
        allImagesLayout.addComponent(gallery);
        this.addComponent(allImagesLayout);
    }

    public void init() {
        List<com.github.lotsabackscatter.blueimp.gallery.Image> imageList =
                images.stream().map(img -> new Builder()
                      .title(img.getCaption())
                      .href(getResourceURL(img, img.getCaption()))
                      .thumbnail(getResourceURL(img, img.getCaption()))
                      .build())
                      .collect(toList());

        images.forEach(img -> img.addClickListener(event ->
                gallery.showGallery(imageList)
        ));
//        gallery.showGallery(imageList);
    }

    private String getResourceURL(AbstractClientConnector connector, String fileName) {
        String domain       = UI.getCurrent().getPage().getLocation().getSchemeSpecificPart();
        Integer uiId        = connector.getUI().getUIId();
        String connectorId  = connector.getConnectorId();
        return domain + "APP/connector/" + uiId + "/" + connectorId + "/source/" + fileName;
    }

}
