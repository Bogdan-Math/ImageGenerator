package layers.web.vaadin;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import layers.service.ImageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import utility.helpers.ObjectTypeConverter;
import utility.helpers.PatternManager;
import utility.helpers.ResourceReader;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class ImageUploader implements Upload.StartedListener, Upload.Receiver, Upload.ProgressListener, Upload.SucceededListener, Upload.FinishedListener {

    @Autowired
    private Upload upload;

    @Autowired
    private ImageGenerator imageGenerator;

    @Autowired
    private PatternManager patternManager;

    @Autowired
    private ResourceReader resourceReader;

    @Autowired
    private ObjectTypeConverter converter;

    private ByteArrayOutputStream uploadedImage;
    private Image originalImageView  = new Image("");
    private Image generatedImageView = new Image("");

    @Override
    public void uploadStarted(Upload.StartedEvent startedEvent) {

        originalImageView.setVisible(false);
        generatedImageView.setVisible(false);

        if (!"image/jpeg".equals(startedEvent.getMIMEType())) {
            startedEvent.getUpload().interruptUpload();

            String caption = "Oh, no! Only '.jpg' and '.jpeg' files can be uploaded.";
            Notification.show(caption, Notification.Type.WARNING_MESSAGE);
        }
    }

    @Override
    public OutputStream receiveUpload(String fileName, String mimeType) {

        this.uploadedImage = new ByteArrayOutputStream();

        return uploadedImage;
    }

    @Override
    public void updateProgress(long readBytes, long contentLength) {

        int maxSize = 10485760; // 10485760 (Bytes) = 10MB

        if (maxSize < contentLength) {
            upload.interruptUpload();

            String caption = "Oh, no! File size can not be more then 10 MB.";
            Notification.show(caption, Notification.Type.WARNING_MESSAGE);
        }
    }

    @Override
    public void uploadSucceeded(Upload.SucceededEvent event) {

        String fileName = event.getFilename();

        imageGenerator.setExpectedColumnsNumber(100)
                .setPatterns(patternManager.patternsMap(resourceReader.readFiles("images/flags")))
                .setImage(converter.bufferedImage(uploadedImage.toByteArray()));

        BufferedImage bufferedImage = imageGenerator.generateImage();

        originalImageView.setSource(new StreamResource(() ->
                converter.inputStream(uploadedImage.toByteArray()),
                "original_" + fileName));

        generatedImageView.setSource(new StreamResource(() ->
                converter.inputStream(bufferedImage),
                "generated_" + fileName));

        originalImageView.setVisible(true);
        generatedImageView.setVisible(true);
    }

    @Override
    public void uploadFinished(Upload.FinishedEvent finishedEvent) {
        originalImageView.setVisible(true);
        generatedImageView.setVisible(true);
    }

    public Image getOriginalImageView() {
        return originalImageView;
    }

    public Image getGeneratedImageView() {
        return generatedImageView;
    }
}
