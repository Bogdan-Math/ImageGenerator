package layers.web.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Link;
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
        Link githubLink               = githubLink();
        Link codacyLink               = codacyLink();

        verticalLayout.addComponents(githubLink, codacyLink, uploadComponent, patternsGroup, slider, images);

        verticalLayout.setComponentAlignment(githubLink, Alignment.TOP_RIGHT);
        verticalLayout.setComponentAlignment(codacyLink, Alignment.TOP_LEFT);
        verticalLayout.setComponentAlignment(uploadComponent, Alignment.TOP_CENTER);
        verticalLayout.setComponentAlignment(patternsGroup, Alignment.TOP_CENTER);

        setContent(verticalLayout);
    }

    private Link githubLink() {
        Link githubLink = link("", "https://github.com/Bogdan-Math/ImageGenerator");
        githubLink.setIcon(new ExternalResource("https://camo.githubusercontent.com/a6677b08c955af8400f44c6298f40e7d19cc5b2d/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f677261795f3664366436642e706e67"));
        githubLink.setStyleName("github-link");
        return githubLink;
    }

    private Link codacyLink() {
        Link codacyLink = link("", "https://www.codacy.com/app/bogdan-math-stepanov/ImageGenerator/dashboard");
        codacyLink.setIcon(new ExternalResource("https://api.codacy.com/project/badge/Grade/433e9ca7250d46f2bbd6280b7db5768c"));
        return codacyLink;
    }

    private Link link(String caption, String sourceURL) {
        Link link                 = new Link();
        ExternalResource resource = new ExternalResource(sourceURL);
//        ExternalResource iconLink = new ExternalResource("https://api.codacy.com/project/badge/Grade/433e9ca7250d46f2bbd6280b7db5768c");

        link.setCaption(caption);
        link.setResource(resource);
//        toGithub.setIcon(iconLink);

        return link;
    }


}