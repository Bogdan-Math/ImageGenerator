package layers.web.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


@SpringUI(path = "/*")
@Scope("prototype")
@Theme("mytheme")
@Title("Image Generator")
public class ImageGeneratorUI extends UI {

    @Autowired
    private ImageUploader imageUploader;

    @Autowired
    private Upload upload;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        upload.addSucceededListener(imageUploader);
        upload.addFinishedListener(imageUploader);
        upload.addProgressListener(imageUploader);
        upload.setImmediateMode(true);
        upload.setButtonCaption("select and generate image");

        VerticalLayout verticalLayout = new VerticalLayout();
        Link githubLink               = githubLink();
        Link codacyLink               = codacyLink();
        verticalLayout.addComponents(githubLink, codacyLink, upload);
        verticalLayout.setComponentAlignment(githubLink, Alignment.TOP_RIGHT);
        verticalLayout.setComponentAlignment(codacyLink, Alignment.TOP_RIGHT);
        verticalLayout.setComponentAlignment(upload, Alignment.TOP_CENTER);

        Image originalImageView  = imageUploader.getOriginalImageView();
        Image generatedImageView = imageUploader.getGeneratedImageView();
        originalImageView.setSource(null);
        generatedImageView.setSource(null);

        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setSizeFull();
        gridLayout.setSpacing(true);
        gridLayout.addComponent(originalImageView);
        gridLayout.addComponent(generatedImageView);

        originalImageView.setSizeFull();// Width("50%");
        originalImageView.setStyleName("bordered");

        generatedImageView.setSizeFull();// setWidth("50%");
        generatedImageView.setStyleName("bordered");

        verticalLayout.addComponent(gridLayout);

        setContent(verticalLayout);
    }

    private Link githubLink() {
        return link("GitHub", "https://github.com/Bogdan-Math/ImageGenerator");
    }

    private Link codacyLink() {
        return link("Codacy", "https://www.codacy.com/app/bogdan-math-stepanov/ImageGenerator/dashboard");
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