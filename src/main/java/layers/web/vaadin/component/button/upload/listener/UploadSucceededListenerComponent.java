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
import utility.pattern.InformationalImage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static domain.Settings.*;

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

        String timeNow                   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
        String imageFileName             = succeededEvent.getFilename();
        byte[] uploadedBytes             = receiver.getUploadStream().toByteArray();
        InformationalImage uploadedImage = converter.informationalImage(uploadedBytes);

        if ((uploadedImage.getWidth() < INCOME_IMAGE_ALLOWED_MIN_WIDTH) || (uploadedImage.getHeight() < INCOME_IMAGE_ALLOWED_MIN_HEIGHT)) {
            notification.add("Image resolution can't be less than " + INCOME_IMAGE_ALLOWED_MIN_WIDTH + " x " + INCOME_IMAGE_ALLOWED_MIN_HEIGHT + " (px)");
            return;
        }

        if ((uploadedImage.getWidth() > INCOME_IMAGE_ALLOWED_MAX_WIDTH) || (uploadedImage.getHeight() > INCOME_IMAGE_ALLOWED_MAX_HEIGHT)) {
            notification.add("Image resolution can't be more than " + INCOME_IMAGE_ALLOWED_MAX_WIDTH + " x " + INCOME_IMAGE_ALLOWED_MAX_HEIGHT + " (px)");
            return;
        }

        settings.setImageFileName(imageFileName);
        settings.setIncomeImage(uploadedImage);

        originalImageView.setSource(new StreamResource(() ->
                converter.inputStream(settings.getIncomeImage()),
                String.join("_", "original", timeNow, settings.getImageFileName())));

        notification.add("Upload succeeded.");
    }
}