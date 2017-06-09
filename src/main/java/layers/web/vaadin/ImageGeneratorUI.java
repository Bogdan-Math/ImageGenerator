package layers.web.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
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

        Image originalImageView = imageUploader.getOriginalImageView();
        Image generatedImageView = imageUploader.getGeneratedImageView();

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(upload);
        verticalLayout.setComponentAlignment(upload, Alignment.TOP_CENTER);

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
}