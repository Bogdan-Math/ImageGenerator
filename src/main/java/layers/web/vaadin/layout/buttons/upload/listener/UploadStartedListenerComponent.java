package layers.web.vaadin.layout.buttons.upload.listener;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.StartedEvent;
import layers.web.vaadin.additional.NotificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("session")
public class UploadStartedListenerComponent implements UploadStartedListener {

    private Upload upload;

    @Autowired
    private NotificationBuilder notificationBuilder;

    @Override
    public void uploadStarted(StartedEvent startedEvent) {
        this.upload = startedEvent.getUpload();

        notificationBuilder.add("Upload started.");
        if (!"image/jpeg".equals(startedEvent.getMIMEType())) {
            upload.interruptUpload();

            notificationBuilder.add("Oh, no! Only '.jpg' and '.jpeg' files can be uploaded.");
        }
    }

    @Override
    public Upload getUpload() {
        return upload;
    }
}