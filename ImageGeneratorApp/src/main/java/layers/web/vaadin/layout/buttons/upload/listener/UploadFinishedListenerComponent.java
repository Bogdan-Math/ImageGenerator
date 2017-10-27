package layers.web.vaadin.layout.buttons.upload.listener;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload.FinishedEvent;
import layers.web.vaadin.additional.NotificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import static com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION;

@SpringComponent
@Scope("session")
public class UploadFinishedListenerComponent implements UploadFinishedListener {

    @Autowired
    private NotificationBuilder notificationBuilder;

    @Override
    public void uploadFinished(FinishedEvent finishedEvent) {
        notificationBuilder.showAsString(TRAY_NOTIFICATION);
    }
}
