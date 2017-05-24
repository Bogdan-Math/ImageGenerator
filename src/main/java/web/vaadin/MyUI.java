package web.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Upload.SucceededEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Theme("mytheme")
@Title("Image Generator")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final Embedded image = new Embedded("Uploaded Image");
        image.setVisible(false);

        class ImageUploader implements Upload.Receiver, Upload.SucceededListener {
            public File file;

            public OutputStream receiveUpload(String filename,
                                              String mimeType) {
                // Create upload stream
                FileOutputStream fos = null; // Stream to write to
                try {
                    // Open the file for writing.
                    file = new File("/tmp/uploads/" + filename);
                    fos = new FileOutputStream(file);
                } catch (final java.io.FileNotFoundException e) {
                    new Notification("Could not open file<br/>",
                            e.getMessage(),
                            Notification.Type.ERROR_MESSAGE)
                            .show(Page.getCurrent());
                    return null;
                }
                return fos; // Return the output stream to write to
            }

            public void uploadSucceeded(SucceededEvent event) {
                // Show the uploaded file in the image viewer
                image.setVisible(true);
                image.setSource(new FileResource(file));
            }
        };

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
//        image.setHeight("25%");
        panel.setContent(panelContent);

        setContent(panelContent);

    }

}
