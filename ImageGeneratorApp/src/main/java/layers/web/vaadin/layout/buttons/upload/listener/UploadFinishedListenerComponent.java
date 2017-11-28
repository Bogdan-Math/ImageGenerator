package layers.web.vaadin.layout.buttons.upload.listener;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload.FinishedEvent;
import layers.web.vaadin.additional.NotificationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import static com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION;
import static layers.web.vaadin.additional.NotificationManager.showAsString;

@SpringComponent
@Scope("session")
public class UploadFinishedListenerComponent implements UploadFinishedListener {

    @Autowired
    private NotificationManager notificationManager;

    @Override
    public void uploadFinished(FinishedEvent finishedEvent) {
        showAsString(notificationManager.build(), TRAY_NOTIFICATION);
    }
}
