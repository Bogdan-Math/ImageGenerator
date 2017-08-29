package layers.web.vaadin;

import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import layers.service.ImageGenerator;
import layers.web.vaadin.listeners.UploadReceiver;
import layers.web.vaadin.listeners.UploadStartedListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import utility.helpers.ObjectTypeConverter;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@Scope("session")
public class UploadComponentListener implements Upload.ProgressListener, Upload.SucceededListener, Upload.FinishedListener {

    @Autowired
    private UploadReceiver receiver;

    @Autowired
    private UploadStartedListener startedListener;

    @Autowired
    private ImageGenerator imageGenerator;

    @Autowired
    private ObjectTypeConverter converter;

    @Resource(name = "notifications")
    private List<String> notifications;

    @Autowired
    @Qualifier(value = "originalImageView")
    private Image originalImageView;

    @Autowired
    @Qualifier(value = "generatedImageView")
    private Image generatedImageView;

    @Override
    public void updateProgress(long readBytes, long contentLength) {

        int maxSize = 10485760; // 10485760 (Bytes) = 10MB

        if (maxSize < contentLength) {
            startedListener.getUpload().interruptUpload();

            notifications.add("Oh, no! File size can not be more then 10 MB.");
        }
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {

        String fileName             = event.getFilename();
        byte[] uploadedBytes        = receiver.getUploadStream().toByteArray();
        BufferedImage uploadedImage = converter.bufferedImage(uploadedBytes);

        if ((uploadedImage.getWidth() > 2560) || (uploadedImage.getHeight() > 2048)) {
            notifications.add("Image resolution can't be more than 2560 x 2048 (px)");
            return;
        }

        notifications.add("Upload succeeded.");

        imageGenerator.setImage(uploadedImage);

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

    @Override
    public void uploadFinished(Upload.FinishedEvent finishedEvent) {
        Notification.show(notifications.stream()
                                       .map(notification -> "- " + notification)
                                       .collect(Collectors.joining("\n")),
                          Notification.Type.TRAY_NOTIFICATION);
        notifications.clear();
    }
}
