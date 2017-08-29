package layers.web.vaadin.listeners;

import com.vaadin.spring.annotation.SpringComponent;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@SpringComponent
public class UploadReceiverComponent implements UploadReceiver {

    private ByteArrayOutputStream uploadStream;

    @Override
    public OutputStream receiveUpload(String fileName, String mimeType) {
        this.uploadStream = new ByteArrayOutputStream();
        return uploadStream;
    }

    public ByteArrayOutputStream getUploadStream() {
        return uploadStream;
    }
}