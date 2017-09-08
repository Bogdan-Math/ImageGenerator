package layers.web.vaadin.component.listener;

import com.vaadin.ui.Upload;

import java.io.ByteArrayOutputStream;

public interface UploadReceiver extends Upload.Receiver {

    ByteArrayOutputStream getUploadStream();
}