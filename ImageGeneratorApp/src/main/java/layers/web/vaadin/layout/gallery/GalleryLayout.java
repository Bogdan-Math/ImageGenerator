package layers.web.vaadin.layout.gallery;

import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import system.ResourceReader;

import javax.annotation.PostConstruct;

import static domain.PatternType.COMMONS;

@SpringComponent
@Scope("session")
public class GalleryLayout extends VerticalLayout {

    @Autowired
    private ResourceReader resourceReader;

    @PostConstruct
    public void postConstruct() {
        HorizontalLayout allImagesLayout = new HorizontalLayout();
        allImagesLayout.setSizeFull();

        resourceReader.readFiles(COMMONS.getLocation())
                      .stream()
                      .map(imgFile -> new Image() {{
                          setSource(new FileResource(imgFile));
                          setStyleName("gallery-image");
                      }}).forEach(allImagesLayout::addComponent);

        addComponent(allImagesLayout);
    }
}
