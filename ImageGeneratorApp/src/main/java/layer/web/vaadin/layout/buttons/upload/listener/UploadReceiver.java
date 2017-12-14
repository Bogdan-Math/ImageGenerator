package layer.web.vaadin.layout.buttons.upload.listener;

import com.vaadin.ui.Upload;

import java.io.ByteArrayOutputStream;

public interface UploadReceiver extends Upload.Receiver {

    ByteArrayOutputStream getUploadStream();
}