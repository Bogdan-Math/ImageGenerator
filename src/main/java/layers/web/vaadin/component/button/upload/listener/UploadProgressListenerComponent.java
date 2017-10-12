package layers.web.vaadin.component.button.upload.listener;

import com.vaadin.spring.annotation.SpringComponent;
import layers.web.vaadin.component.visual.NotificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("session")
public class UploadProgressListenerComponent implements UploadProgressListener {

    @Autowired
    private UploadStartedListener startedListener;

    @Autowired
    private NotificationBuilder notificationBuilder;

    @Override
    public void updateProgress(long readBytes, long contentLength) {
        int maxSize = 10485760; // 10485760 (Bytes) = 10MB

        if (maxSize < contentLength) {
            startedListener.getUpload().interruptUpload();

            notificationBuilder.add("Oh, no! File size can not be more then 10 MB.");
        }
    }
}
