package layers.web.vaadin.component.button.upload.listener;

import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@SpringComponent
@Scope("session")
public class UploadReceiverComponent implements UploadReceiver {

    private ByteArrayOutputStream uploadStream;

    @Override
    public OutputStream receiveUpload(String fileName, String mimeType) {
        this.uploadStream = new ByteArrayOutputStream();
        return uploadStream;
    }

    @Override
    public ByteArrayOutputStream getUploadStream() {
        return uploadStream;
    }
}