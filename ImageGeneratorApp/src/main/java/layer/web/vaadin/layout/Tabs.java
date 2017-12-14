package layer.web.vaadin.layout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import layer.web.vaadin.layout.buttons.ButtonsLayout;
import layer.web.vaadin.layout.gallery.GalleryLayoutBuilder;
import layer.web.vaadin.layout.images.ImagesLayout;
import layer.web.vaadin.layout.patterns.PatternsGroupLayout;
import layer.web.vaadin.layout.slider.SliderLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class Tabs extends TabSheet {

    public static final String GENERATOR = "GENERATOR";
    public static final String GALLERY   = "GALLERY";

    @Autowired
    private SliderLayout sliderLayout;

    @Autowired
    private PatternsGroupLayout patternsGroupLayout;

    @Autowired
    private ButtonsLayout buttonsLayout;

    @Autowired
    private ImagesLayout imagesLayout;

    @Autowired
    private GalleryLayoutBuilder galleryLayoutBuilder;

    @PostConstruct
    public void postConstruct() {
        addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);

        addTab(generatorTab());
        addTab(galleryTab());

        addSelectedTabChangeListener((SelectedTabChangeListener) event -> {
            if (GALLERY.equals(event.getTabSheet().getSelectedTab().getCaption())) {
                galleryLayoutBuilder.buildGallery();
            }
        });

    }

    private Layout generatorTab () {
        Layout generatorTab = newTab(sliderLayout,
                                     patternsGroupLayout,
                                     buttonsLayout,
                                     imagesLayout);

        generatorTab.setCaption(GENERATOR);
        return generatorTab;
    }

    private Layout galleryTab() {
        Layout galleryTab = newTab(galleryLayoutBuilder);

        galleryTab.setCaption(GALLERY);
        return galleryTab;
    }

    private Layout newTab(Component... components) {
        return new VerticalLayout(components);
    }

}
