package layers.web.vaadin.components.listeners;

import com.vaadin.ui.Upload;

import java.io.ByteArrayOutputStream;

public interface UploadReceiver extends Upload.Receiver {

    ByteArrayOutputStream getUploadStream();
}