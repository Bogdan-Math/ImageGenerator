package layers.web.vaadin;

import com.github.lotsabackscatter.blueimp.gallery.Gallery;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.AbstractClientConnector;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import layers.repository.PatternsRepository;
import layers.web.vaadin.layout.buttons.ButtonsLayout;
import layers.web.vaadin.layout.footer.FooterLayout;
import layers.web.vaadin.layout.header.HeaderLayout;
import layers.web.vaadin.layout.images.ImagesLayout;
import layers.web.vaadin.layout.patterns.PatternsGroupLayout;
import layers.web.vaadin.layout.slider.SliderLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import utility.core.PatternType;
import utility.system.ResourceReader;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


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
    private ResourceReader resourceReader;

    @Autowired
    private PatternsRepository patternsRepository;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout verticalLayout = new VerticalLayout();

        HorizontalLayout allImagesLayout = new HorizontalLayout();
        allImagesLayout.setSizeFull();

        Stream<File> fileStream = resourceReader.readFiles(PatternType.PLAINS.getLocation()).stream();

        List<Image> images = fileStream.map(imgFile -> new Image(imgFile.getName(), new FileResource(imgFile)))
                                       .collect(toList());

        images.forEach(allImagesLayout::addComponent);

        Gallery gallery = new Gallery();
        allImagesLayout.addComponent(gallery);

        Button qwerty = new Button("qwerty");

        verticalLayout.addComponents(qwerty, allImagesLayout,
                                     headerLayout,
                                     sliderLayout,
                                     patternsGroupLayout,
                                     buttonsLayout,
                                     imagesLayout,
                                     footerLayout);

        setContent(verticalLayout);

        List<com.github.lotsabackscatter.blueimp.gallery.Image> imageList =
                images.stream().map(img -> new com.github.lotsabackscatter.blueimp.gallery.Image.Builder()
        .title(img.getCaption())
        .href(getResourceURL(img, img.getCaption()))
        .thumbnail(getResourceURL(img, img.getCaption()))
        .build())
        .collect(toList());

        qwerty.addClickListener(event -> gallery.showGallery(imageList));
//        qwerty.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                gallery.showGallery(imageList);
//                System.out.println(imageList);
//            }
//        });

        images.forEach(img -> img.addClickListener(event ->
                gallery.showGallery(imageList)
        ));

//        gallery.showGallery(imageList);
    }

    private String getResourceURL(AbstractClientConnector connector, String fileName) {

        String protocol = UI.getCurrent().getPage().getLocation().getScheme();
        String currentUrl = UI.getCurrent().getPage().getLocation().getAuthority();
        String cid = connector.getConnectorId();
        Integer uiId = connector.getUI().getUIId();

        return UI.getCurrent().getPage().getLocation()+"APP/connector/"+uiId+"/"+cid+"/source/"+fileName;
    }
}