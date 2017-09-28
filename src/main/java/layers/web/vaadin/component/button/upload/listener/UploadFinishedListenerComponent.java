package layers.web.vaadin.component.button.upload.listener;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload.FinishedEvent;
import layers.web.vaadin.component.visual.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("session")
public class UploadFinishedListenerComponent implements UploadFinishedListener {

    @Autowired
    private Notification notification;

    @Override
    public void uploadFinished(FinishedEvent finishedEvent) {
        notification.show();
    }
}
