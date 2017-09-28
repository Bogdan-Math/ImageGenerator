package layers.web.vaadin.component.button.upload.listener;

import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload;
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
    @Qualifier(value = "originalImageView")
    private Image originalImageView;

    @Resource(name = "notifications")
    private List<String> notifications;

    @Override
    public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {

        String timeNow              = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
        String imageFileName        = succeededEvent.getFilename();
        byte[] uploadedBytes        = receiver.getUploadStream().toByteArray();
        BufferedImage uploadedImage = converter.bufferedImage(uploadedBytes);

        if ((uploadedImage.getWidth() > 2560) || (uploadedImage.getHeight() > 2048)) {
            notifications.add("Image resolution can't be more than 2560 x 2048 (px)");
            return;
        }

        notifications.add("Upload succeeded.");

        settings.setImageFileName(imageFileName);
        settings.setImage(uploadedImage);

        originalImageView.setSource(new StreamResource(() ->
                converter.inputStream(settings.getImage()),
                String.join("_", "original", timeNow, settings.getImageFileName())));
    }
}