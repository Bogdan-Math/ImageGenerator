package layers.web.vaadin.component.layout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import layers.web.vaadin.component.visual.DownloadButton;
import layers.web.vaadin.component.visual.GenerateButton;
import layers.web.vaadin.component.visual.UploadButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
//TODO: add upload and download button
public class ImagesLayout extends GridLayout {

    @Autowired
    @Qualifier(value = "originalImageView")
    private Image originalImageView;

    @Autowired
    @Qualifier(value = "generatedImageView")
    private Image generatedImageView;

    @PostConstruct
    public void postConstruct() {

        setColumns(2);
        setRows(1);
        setSizeFull();
        setSpacing(true);

        originalImageView.setSizeFull();
        generatedImageView.setSizeFull();

        originalImageView.setStyleName("bordered");
        generatedImageView.setStyleName("bordered");

        addComponents(originalImageView, generatedImageView);
    }
}
