package web.vaadin;

import basic.ImageGenerator;
import basic.ObjectTypeConverter;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Upload.SucceededEvent;
import org.springframework.context.annotation.Scope;
import utility.Resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;


@SpringUI(path = "/*")
@Scope("prototype")
@Theme("mytheme")
@Title("Image Generator")
public class MyUI extends UI {

    private ObjectTypeConverter converter = new ObjectTypeConverter();
    private ImageGenerator imageGenerator = new ImageGenerator();
    private Image originalImageView       = new Image("");
    private Image generatedImageView      = new Image("");
    private Upload upload;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        ImageUploader receiver = new ImageUploader();
        upload = new Upload("", receiver);
        upload.addStartedListener(receiver);
        upload.addSucceededListener(receiver);
        upload.addFinishedListener(receiver);
        upload.addProgressListener(receiver);
        upload.setImmediateMode(true);
        upload.setButtonCaption("select and generate image");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(upload);
        verticalLayout.setComponentAlignment(upload, Alignment.TOP_CENTER);

        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setSizeFull();
        gridLayout.setSpacing(true);
        gridLayout.addComponent(originalImageView);
        gridLayout.addComponent(generatedImageView);

        originalImageView.setSizeFull();// Width("50%");
        originalImageView.setStyleName("bordered");

        generatedImageView.setSizeFull();// setWidth("50%");
        generatedImageView.setStyleName("bordered");

        verticalLayout.addComponent(gridLayout);

        setContent(verticalLayout);
    }

    class ImageUploader implements Upload.StartedListener, Upload.Receiver, Upload.ProgressListener, Upload.SucceededListener, Upload.FinishedListener {

        private ByteArrayOutputStream uploadedImage;

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
        public void uploadSucceeded(SucceededEvent event) {

            String fileName = event.getFilename();

            imageGenerator.setExpectedColumnsNumber(100)
                    .setPatterns(new Resource().getPatternsFrom("images/flags"))
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
    }

}
