package layers.web.vaadin.component.visual;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import domain.ImageGenerator;
import domain.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utility.helper.ObjectTypeConverter;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Scope("session")
public class GenerateButton extends Button {

    @Autowired
    private ImageGenerator imageGenerator;

    @Autowired
    private ObjectTypeConverter converter;

    @Autowired
    private Settings settings;

    @Autowired
    @Qualifier(value = "generatedImageView")
    private Image generatedImageView;

    @Resource(name = "notifications")
    private List<String> notifications;

    @Autowired
    private DownloadButton downloadButton;

    @PostConstruct
    public void postConstruct() {
        setCaption("generate");
        setEnabled(false);
        addClickListener((ClickListener) event -> {

            BufferedImage generatedImage = imageGenerator.generateImage();
            String timeNow               = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

            generatedImageView.setSource(new StreamResource(() ->
                    converter.inputStream(generatedImage),
                    String.join("_", "generated", timeNow, settings.getImageFileName())));

            notifications.add("Your image was generated.");

            downloadButton.getFileDownloader().setFileDownloadResource(generatedImageView.getSource());
            downloadButton.setEnabled(true);
        });
    }
}
