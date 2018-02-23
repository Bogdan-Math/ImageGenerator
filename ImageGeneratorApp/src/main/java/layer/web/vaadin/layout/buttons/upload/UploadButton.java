package layer.web.vaadin.layout.buttons.upload;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload;
import layer.web.vaadin.layout.buttons.upload.listener.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class UploadButton extends Upload {

    private static final String UPLOAD_BUTTON_ID = "upload-button-id";
    private static final String UPLOAD_IMAGE     = "upload image";

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
        setId(UPLOAD_BUTTON_ID);
        setButtonCaption(UPLOAD_IMAGE);
        setReceiver(receiver);
        addStartedListener(startedListener);
        addProgressListener(progressListener);
        addSucceededListener(succeededListener);
        addFinishedListener(finishedListener);
        setImmediateMode(true);
    }
}
