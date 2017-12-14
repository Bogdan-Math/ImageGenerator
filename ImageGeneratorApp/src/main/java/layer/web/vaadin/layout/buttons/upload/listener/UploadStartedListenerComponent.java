package layer.web.vaadin.layout.buttons.upload.listener;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.StartedEvent;
import layer.web.vaadin.additional.NotificationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("session")
public class UploadStartedListenerComponent implements UploadStartedListener {

    private Upload upload;

    @Autowired
    private NotificationManager notificationManager;

    @Override
    public void uploadStarted(StartedEvent startedEvent) {
        this.upload = startedEvent.getUpload();

        notificationManager.add("Upload started.");
        if (!"image/jpeg".equals(startedEvent.getMIMEType())) {
            upload.interruptUpload();

            notificationManager.add("Oh, no! Only '.jpg' and '.jpeg' files can be uploaded.");
        }
    }

    @Override
    public Upload getUpload() {
        return upload;
    }
}