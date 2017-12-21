package layer.web.vaadin.layout.buttons.upload.listener;

import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload.SucceededEvent;
import core.Settings;
import domain.InformationalImage;
import layer.web.vaadin.additional.NotificationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static core.Settings.*;

@SpringComponent
@Scope("session")
public class UploadSucceededListenerComponent implements UploadSucceededListener {

    @Autowired
    private UploadReceiver receiver;

    @Autowired
    private Settings settings;

    @Autowired
    @Qualifier(value = "originalImageView")
    private Image originalImageView;

    @Autowired
    private NotificationManager notificationManager;

    @Override
    public void uploadSucceeded(SucceededEvent succeededEvent) {

        String timeNow                   = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
        String imageFileName             = succeededEvent.getFilename();
        byte[] uploadedBytes             = receiver.getUploadStream().toByteArray();
        InformationalImage uploadedImage = new InformationalImage(uploadedBytes);

        if (uploadedImage.widthLessThan(INCOME_IMAGE_ALLOWED_MIN_WIDTH) || uploadedImage.heightLessThan(INCOME_IMAGE_ALLOWED_MIN_HEIGHT)) {
            notificationManager.add("Image resolution can't be less than " + INCOME_IMAGE_ALLOWED_MIN_WIDTH + " x " + INCOME_IMAGE_ALLOWED_MIN_HEIGHT + " (px)");
            return;
        }

        if (uploadedImage.widthMoreThan(INCOME_IMAGE_ALLOWED_MAX_WIDTH) || uploadedImage.heightMoreThan(INCOME_IMAGE_ALLOWED_MAX_HEIGHT)) {
            notificationManager.add("Image resolution can't be more than " + INCOME_IMAGE_ALLOWED_MAX_WIDTH + " x " + INCOME_IMAGE_ALLOWED_MAX_HEIGHT + " (px)");
            return;
        }

        settings.setIncomeImageName(imageFileName);
        settings.setIncomeImage(uploadedImage);

        originalImageView.setSource(new StreamResource(() ->
                settings.getIncomeImage().asStream(),
                String.join("_", "original", timeNow, settings.getIncomeImageName())));

        notificationManager.add("Upload succeeded.");
    }
}