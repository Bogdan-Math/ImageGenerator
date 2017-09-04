package layers.web.vaadin.listeners;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
import java.util.List;

@SpringComponent
@Scope("session")
public class UploadStartedListenerComponent implements UploadStartedListener {

    private Upload upload;

    @Resource(name = "notifications")
    private List<String> notifications;

    @Override
    public void uploadStarted(Upload.StartedEvent startedEvent) {
        this.upload = startedEvent.getUpload();

        notifications.add("Upload started.");
        if (!"image/jpeg".equals(startedEvent.getMIMEType())) {
            upload.interruptUpload();

            notifications.add("Oh, no! Only '.jpg' and '.jpeg' files can be uploaded.");
        }
    }

    @Override
    public Upload getUpload() {
        return upload;
    }
}