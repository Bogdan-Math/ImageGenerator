package layers.web.vaadin.component.button.upload.listener;

import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload.SucceededEvent;
import domain.Settings;
import layers.web.vaadin.component.visual.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import utility.helper.ObjectTypeConverter;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    @Qualifier(value = "originalImageView")
    private Image originalImageView;

    @Autowired
    private Notification notification;

    @Override
    public void uploadSucceeded(SucceededEvent succeededEvent) {

        String timeNow              = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
        String imageFileName        = succeededEvent.getFilename();
        byte[] uploadedBytes        = receiver.getUploadStream().toByteArray();
        BufferedImage uploadedImage = converter.bufferedImage(uploadedBytes);

        if ((uploadedImage.getWidth() > 2560) || (uploadedImage.getHeight() > 2048)) {
            notification.add("Image resolution can't be more than 2560 x 2048 (px)");
            return;
        }

        notification.add("Upload succeeded.");

        settings.setImageFileName(imageFileName);
        settings.setImage(uploadedImage);

        originalImageView.setSource(new StreamResource(() ->
                converter.inputStream(settings.getImage()),
                String.join("_", "original", timeNow, settings.getImageFileName())));
    }
}