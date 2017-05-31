package web.vaadin;

import basic.ImageGenerator;
import basic.ObjectTypeConverter;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload.SucceededEvent;
import org.springframework.context.annotation.Scope;
import utility.FileReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@SpringUI(path = "/*")
@Scope("prototype")
@Theme("mytheme")
@Title("Image Generator")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final Embedded image = new Embedded("Uploaded Image");
        image.setVisible(false);

        class ImageUploader implements Upload.Receiver, Upload.SucceededListener {
            private ByteArrayInputStream uploadData;
            private String fileName;

            @Override
            public OutputStream receiveUpload(String fileName, String mimeType) {
                this.fileName = fileName;
                return new ByteArrayOutputStream() {
                    @Override
                    public void close() throws IOException {
                        uploadData = new ByteArrayInputStream(toByteArray());
                    }
                };


            }
            @Override
            public void uploadSucceeded(SucceededEvent event) {
                // Show the uploaded file in the image viewer
                image.setVisible(true);
                ImageGenerator imageGenerator = new ImageGenerator();
                imageGenerator.setExpectedColumnsNumber(300)
                        .setPatterns(patterns("images/colors"))
                        .setImage(new ObjectTypeConverter().bufferedImageFromInputStream(uploadData));

                File output = new File("/home/bmath/Стільниця/" + fileName);
                try {
                    ImageIO.write(imageGenerator.makeImage(), "jpg", output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                image.setSource(createStreamResource());
                output.deleteOnExit();
            }
        }

        ImageUploader receiver = new ImageUploader();

        // Create the upload with a caption and set receiver later
        Upload upload = new Upload("Upload Image Here", receiver);
        upload.setButtonCaption("Start Upload");
        upload.addSucceededListener(receiver);

        // Put the components in a panel
        Panel panel = new Panel("Cool Image Storage");
        Layout panelContent = new VerticalLayout();
        panelContent.addComponents(upload, image);
        image.setWidth("100%");
        panel.setContent(panelContent);

        setContent(panelContent);

    }

    private StreamResource createStreamResource() {
        return new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                String text = "Date: " + DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM).format(new Date());

                BufferedImage bi = new BufferedImage(370, 30,
                        BufferedImage.TYPE_3BYTE_BGR);
                bi.getGraphics().drawChars(text.toCharArray(), 0,
                        text.length(), 10, 20);

                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ImageIO.write(bi, "png", bos);
                    return new ByteArrayInputStream(bos.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }, "dateImage.png");
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
