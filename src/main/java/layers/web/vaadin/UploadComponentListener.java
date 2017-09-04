package layers.web.vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload;
import org.springframework.context.annotation.Scope;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@Scope("session")
public class UploadComponentListener implements Upload.FinishedListener {

    @Resource(name = "notifications")
    private List<String> notifications;

    @Override
    public void uploadFinished(Upload.FinishedEvent finishedEvent) {
        Notification.show(notifications.stream()
                                       .map(notification -> "- " + notification)
                                       .collect(Collectors.joining("\n")),
                          Notification.Type.TRAY_NOTIFICATION);
        notifications.clear();
    }
}