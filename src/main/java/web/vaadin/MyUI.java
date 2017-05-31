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
    private Embedded image = new Embedded("");

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        ImageUploader receiver = new ImageUploader();
        Upload upload = new Upload("", receiver);
        upload.setImmediateMode(true);
        upload.setButtonCaption("select and generate image");
        upload.addSucceededListener(receiver);

        VerticalLayout panelContent = new VerticalLayout();

        panelContent.addComponent(upload);
        panelContent.setComponentAlignment(upload, Alignment.TOP_CENTER);

        panelContent.addComponent(image);
        panelContent.setComponentAlignment(upload, Alignment.BOTTOM_CENTER);

        image.setWidth("100%");

        setContent(panelContent);
    }

    class ImageUploader implements Upload.Receiver, Upload.SucceededListener {
        private ByteArrayInputStream uploadData;

        @Override
        public OutputStream receiveUpload(String fileName, String mimeType) {
            return new ByteArrayOutputStream() {

                @Override
                public void close() throws IOException {
                    uploadData = new ByteArrayInputStream(toByteArray());
                }
            };
        }

        @Override
        public void uploadSucceeded(SucceededEvent event) {
            imageGenerator.setExpectedColumnsNumber(300)
                    .setPatterns(patterns("images/colors"))
                    .setImage(converter.bufferedImageFromInputStream(uploadData));

            image.setSource(new StreamResource((StreamResource.StreamSource) () ->
                    converter.inputStreamFromBufferedImage( imageGenerator.makeImage(), "jpg"),
                    "dateImage.png"));
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
