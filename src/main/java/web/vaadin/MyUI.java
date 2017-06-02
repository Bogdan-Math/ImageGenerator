package web.vaadin;

import basic.ImageGenerator;
import basic.ObjectTypeConverter;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Upload.SucceededEvent;
import org.springframework.context.annotation.Scope;
import utility.FileReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@SpringUI(path = "/*")
@Scope("prototype")
@Theme("mytheme")
@Title("Image Generator")
public class MyUI extends UI {

    private ObjectTypeConverter converter = new ObjectTypeConverter();
    private ImageGenerator imageGenerator = new ImageGenerator();
    private Image originalImage  = new Image("");
    private Image generatedImage = new Image("");

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        ImageUploader receiver = new ImageUploader();
        Upload upload = new Upload("", receiver);
        upload.setImmediateMode(true);
        upload.setButtonCaption("select and generate image");
        upload.addSucceededListener(receiver);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponent(upload);
        verticalLayout.setComponentAlignment(upload, Alignment.TOP_CENTER);

        GridLayout gridLayout = new GridLayout(2, 1);
        gridLayout.setSizeFull();
        gridLayout.setSpacing(true);
        gridLayout.addComponent(originalImage);
        gridLayout.addComponent(generatedImage);

        originalImage.setSizeFull();// Width("50%");
        originalImage.setStyleName("bordered");

        generatedImage.setSizeFull();// setWidth("50%");
        generatedImage.setStyleName("bordered");

        verticalLayout.addComponent(gridLayout);

        setContent(verticalLayout);
    }

    class ImageUploader implements Upload.Receiver, Upload.SucceededListener {

        private ByteArrayOutputStream uploadedImage;
        private String fileName;

        @Override
        public OutputStream receiveUpload(String fileName, String mimeType) {

            this.uploadedImage = new ByteArrayOutputStream();
            this.fileName = fileName;

            return uploadedImage;
        }

        @Override
        public void uploadSucceeded(SucceededEvent event) {

            imageGenerator.setExpectedColumnsNumber(300)
                    .setPatterns(patterns("images/colors"))
                    .setImage(converter.bufferedImageFromByteArray(uploadedImage.toByteArray()));

            originalImage.setSource(new StreamResource((StreamResource.StreamSource) () ->
                     converter.inputStream(uploadedImage.toByteArray()),
                    "original_" + fileName));

            generatedImage.setSource(new StreamResource((StreamResource.StreamSource) () ->
                    converter.inputStream(imageGenerator.makeImage(), "jpg"),
                    "generated_" + fileName));

        }
    }

    private Map<Color, BufferedImage> patterns(String resourcePath) {
        FileReader fileReader = new FileReader();
        ImageGenerator imageGenerator = new ImageGenerator();
        ObjectTypeConverter objectTypeConverter = new ObjectTypeConverter();

        return Arrays.stream(Optional.ofNullable(fileReader.getFileObject(resourcePath).listFiles())
                .orElseThrow(() -> new RuntimeException("Directory \'" + resourcePath + "\': is not exist or empty.")))
                .filter(File::isFile)
                .collect(Collectors
                        .toMap(
                                file -> imageGenerator.setImage(objectTypeConverter.bufferedImageFromFile(file)).averagedColor(),
                                objectTypeConverter::bufferedImageFromFile,
                                (img_color_1, img_color_2) -> {
                                    System.out.println("Two same average color: ");
                                    System.out.println(img_color_1);
                                    System.out.println(img_color_2);

                                    return img_color_1;
                                }
                        )
                );
    }


}
