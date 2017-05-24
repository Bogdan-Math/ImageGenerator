package web.vaadin;

import basic.ImageGenerator;
import basic.ObjectTypeConverter;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload.SucceededEvent;
import utility.*;
import utility.FileReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

/*
            public ByteArrayInputStream getUploadData() {
                return uploadData;
            }
*/

            @Override
            public OutputStream receiveUpload(String fileName, String mimeType) {
                this.fileName = fileName;
                return new ByteArrayOutputStream() {
                    @Override
                    public void close() throws IOException {
                        uploadData = new ByteArrayInputStream(toByteArray());
                    }
                };


/*
                try {
                    */
/*
                        Here, we'll stored the uploaded file as a temporary file. No doubt there's
                        a way to use a ByteArrayOutputStream, a reader around it, use ProgressListener (and
                        a progress bar) and a separate reader thread to populate a container *during*
                        the update.
                        This is quick and easy example, though.
                    *//*

                    Resource resource = new Resource(filename);
                    tempFile = File.createTempFile(resource.getOnlyName(), resource.getOnlyExtension());

                    return new FileOutputStream(tempFile);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
*/
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
                image.setSource(new FileResource(output));
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
