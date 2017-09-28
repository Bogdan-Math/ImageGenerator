package layers.web.vaadin.component.button.generate.listener;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Image;
import domain.ImageGenerator;
import domain.Settings;
import layers.web.vaadin.component.button.download.listener.Downloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utility.helper.ObjectTypeConverter;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Scope("session")
public class ClickListenerComponent implements ClickListener {

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
    private Downloader downloader;

    @Override
    public void buttonClick(ClickEvent event) {
        if (settings.getImage() != null) {
            BufferedImage generatedImage = imageGenerator.generateImage();
            String timeNow               = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

            generatedImageView.setSource(new StreamResource(() ->
                    converter.inputStream(generatedImage),
                    String.join("_", "generated", timeNow, settings.getImageFileName())));

            notifications.add("Your image was generated.");

            downloader.setFileDownloadResource(generatedImageView.getSource());
        }
    }
}
