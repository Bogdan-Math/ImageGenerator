package layers.web.vaadin.component.button.upload.listener;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.StartedEvent;
import layers.web.vaadin.component.visual.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("session")
public class UploadStartedListenerComponent implements UploadStartedListener {

    private Upload upload;

    @Autowired
    private Notification notification;

    @Override
    public void uploadStarted(StartedEvent startedEvent) {
        this.upload = startedEvent.getUpload();

        notification.add("Upload started.");
        if (!"image/jpeg".equals(startedEvent.getMIMEType())) {
            upload.interruptUpload();

            notification.add("Oh, no! Only '.jpg' and '.jpeg' files can be uploaded.");
        }
    }

    @Override
    public Upload getUpload() {
        return upload;
    }
}