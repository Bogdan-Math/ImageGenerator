package layers.web.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import layers.web.vaadin.layout.buttons.ButtonsLayout;
import layers.web.vaadin.layout.footer.FooterLayout;
import layers.web.vaadin.layout.gallery.GalleryLayout;
import layers.web.vaadin.layout.header.HeaderLayout;
import layers.web.vaadin.layout.images.ImagesLayout;
import layers.web.vaadin.layout.patterns.PatternsGroupLayout;
import layers.web.vaadin.layout.slider.SliderLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@SpringUI(path = "/*")
@Scope("prototype")
@Theme("mytheme")
@Title("Image Generator")
public class ImageGeneratorUI extends UI {

    @Autowired
    private HeaderLayout headerLayout;

    @Autowired
    private SliderLayout sliderLayout;

    @Autowired
    private PatternsGroupLayout patternsGroupLayout;

    @Autowired
    private ButtonsLayout buttonsLayout;

    @Autowired
    private ImagesLayout imagesLayout;

    @Autowired
    private FooterLayout footerLayout;

    @Autowired
    private GalleryLayout galleryLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(headerLayout);

        TabSheet tabsheet = new TabSheet();
        tabsheet.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        verticalLayout.addComponent(tabsheet);

        VerticalLayout generatorTab = new VerticalLayout();
        generatorTab.addComponents(sliderLayout,
                           patternsGroupLayout,
                           buttonsLayout,
                           imagesLayout,
                           footerLayout);
        tabsheet.addTab(generatorTab, "Generator");

        VerticalLayout galleryTab = new VerticalLayout();
        galleryTab.addComponents(galleryLayout);
        galleryTab.setCaption("Gallery");
        tabsheet.addTab(galleryTab);

        setContent(verticalLayout);
    }
}