package layers.web.vaadin.component.listener;

import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload;
import domain.ImageGenerator;
import domain.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import utility.helper.ObjectTypeConverter;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringComponent
@Scope("session")
public class UploadSucceededListenerComponent implements UploadSucceededListener {

    @Autowired
    private UploadReceiver receiver;

    @Autowired
    private ObjectTypeConverter converter;

    @Autowired
    private Settings settings;

    @Autowired
    private ImageGenerator imageGenerator;

    @Resource(name = "notifications")
    private List<String> notifications;

    @Autowired
    @Qualifier(value = "originalImageView")
    private Image originalImageView;

    @Autowired
    @Qualifier(value = "generatedImageView")
    private Image generatedImageView;

    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {

        String fileName             = succeededEvent.getFilename();
        byte[] uploadedBytes        = receiver.getUploadStream().toByteArray();
        BufferedImage uploadedImage = converter.bufferedImage(uploadedBytes);

        if ((uploadedImage.getWidth() > 2560) || (uploadedImage.getHeight() > 2048)) {
            notifications.add("Image resolution can't be more than 2560 x 2048 (px)");
            return;
        }

        notifications.add("Upload succeeded.");

        settings.setImage(uploadedImage);

        BufferedImage generatedImage = imageGenerator.generateImage();
        String timeNow               = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

        originalImageView.setSource(new StreamResource(() ->
                converter.inputStream(uploadedBytes),
                String.join("_", "original", timeNow, fileName)));

        generatedImageView.setSource(new StreamResource(() ->
                converter.inputStream(generatedImage),
                String.join("_", "generated", timeNow, fileName)));

        notifications.add("Your image was generated.");
    }
}