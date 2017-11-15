package layers.web.vaadin.layout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import layers.web.vaadin.layout.buttons.ButtonsLayout;
import layers.web.vaadin.layout.footer.FooterLayout;
import layers.web.vaadin.layout.gallery.GalleryLayout;
import layers.web.vaadin.layout.images.ImagesLayout;
import layers.web.vaadin.layout.patterns.PatternsGroupLayout;
import layers.web.vaadin.layout.slider.SliderLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class Tabs extends TabSheet {

    private static final String GENERATOR = "GENERATOR";
    private static final String GALLERY   = "GALLERY";

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

    @PostConstruct
    public void postConstruct() {
        addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);

        addTab(generatorTab());
        addTab(galleryTab());
    }

    private Layout generatorTab () {
        Layout generatorTab = newTab(sliderLayout,
                                     patternsGroupLayout,
                                     buttonsLayout,
                                     imagesLayout,
                                     footerLayout);

        generatorTab.setCaption(GENERATOR);
        return generatorTab;
    }

    private Layout galleryTab() {
        Layout galleryTab = newTab(galleryLayout);

        galleryTab.setCaption(GALLERY);
        return galleryTab;
    }

    private Layout newTab(Component... components) {
        VerticalLayout newTab = new VerticalLayout();
        newTab.addComponents(components);
        return newTab;
    }

}
