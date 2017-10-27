package layers.web.vaadin.layout.buttons.upload;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload;
import layers.web.vaadin.layout.buttons.upload.listener.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class UploadButton extends Upload {

    @Autowired
    private UploadReceiver receiver;

    @Autowired
    private UploadStartedListener startedListener;

    @Autowired
    private UploadProgressListener progressListener;

    @Autowired
    private UploadSucceededListener succeededListener;

    @Autowired
    private UploadFinishedListener finishedListener;

    @PostConstruct
    public void postConstruct() {
        setButtonCaption("upload image");
        setReceiver(receiver);
        addStartedListener(startedListener);
        addProgressListener(progressListener);
        addSucceededListener(succeededListener);
        addFinishedListener(finishedListener);
        setImmediateMode(true);
    }
}
