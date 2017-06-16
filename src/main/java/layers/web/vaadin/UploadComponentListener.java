package layers.web.vaadin;

import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import layers.service.ImageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import utility.helpers.ObjectTypeConverter;
import utility.helpers.PatternManager;
import utility.helpers.ResourceReader;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@Scope("prototype")
public class UploadComponentListener implements Upload.Receiver, Upload.StartedListener, Upload.ProgressListener, Upload.SucceededListener, Upload.FinishedListener {

    @Autowired
    private ImageGenerator imageGenerator;

    @Autowired
    private PatternManager patternManager;

    @Autowired
    private ResourceReader resourceReader;

    @Autowired
    private ObjectTypeConverter converter;

    @Resource(name = "notifications")
    private List<String> notifications;

    private Upload upload;
    private ByteArrayOutputStream uploadStream;
    private Image originalImageView  = new Image("");
    private Image generatedImageView = new Image("");

    @Override
    public OutputStream receiveUpload(String fileName, String mimeType) {

        this.uploadStream = new ByteArrayOutputStream();

        return uploadStream;
    }

    @Override
    public void uploadStarted(Upload.StartedEvent startedEvent) {

        this.upload = startedEvent.getUpload();

        notifications.add("Upload started.");
        if (!"image/jpeg".equals(startedEvent.getMIMEType())) {
            upload.interruptUpload();

            notifications.add("Oh, no! Only '.jpg' and '.jpeg' files can be uploaded.");
        }
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {

        int maxSize = 10485760; // 10485760 (Bytes) = 10MB

        if (maxSize < contentLength) {
            upload.interruptUpload();

            notifications.add("Oh, no! File size can not be more then 10 MB.");
        }
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {

        String fileName             = event.getFilename();
        byte[] uploadedBytes        = uploadStream.toByteArray();
        BufferedImage uploadedImage = converter.bufferedImage(uploadedBytes);

        if ((uploadedImage.getWidth() > 2560) || (uploadedImage.getHeight() > 2048)) {
            notifications.add("Image resolution can't be more than 2560 x 2048 (px)");
            return;
        }

        notifications.add("Upload succeeded.");

        imageGenerator.setExpectedColumnsNumber(128)
                .setPatterns(patternManager.patternsMap(resourceReader.readFiles("images/colors")))
                .setImage(uploadedImage);

        BufferedImage generatedImage = imageGenerator.generateImage();

        originalImageView.setSource(new StreamResource(() ->
                converter.inputStream(uploadedBytes),
                "original_" + fileName));

        generatedImageView.setSource(new StreamResource(() ->
                converter.inputStream(generatedImage),
                "generated_" + fileName));

        originalImageView.setVisible(true);
        generatedImageView.setVisible(true);

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

    public Image getOriginalImageView() {
        return originalImageView;
    }

    public Image getGeneratedImageView() {
        return generatedImageView;
    }
}
