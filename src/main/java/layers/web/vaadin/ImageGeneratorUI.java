package layers.web.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


@SpringUI(path = "/*")
@Scope("prototype")
@Theme("mytheme")
@Title("Image Generator")
public class ImageGeneratorUI extends UI {

    @Autowired
    private LinksLayout linksLayout;

    @Autowired
    private UploadComponent uploadComponent;

    @Autowired
    private RadioButtonPatternsGroup patternsGroup;

    @Autowired
    private ExpectedColumnsNumberSlider slider;

    @Autowired
    private ImagesLayout images;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.addComponents(linksLayout, uploadComponent, patternsGroup, slider, images);

        verticalLayout.setComponentAlignment(uploadComponent, Alignment.TOP_CENTER);
        verticalLayout.setComponentAlignment(patternsGroup, Alignment.TOP_CENTER);

        setContent(verticalLayout);
    }
}