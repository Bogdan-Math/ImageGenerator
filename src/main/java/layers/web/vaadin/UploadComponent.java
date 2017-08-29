package layers.web.vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload;
import layers.web.vaadin.listeners.UploadReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class UploadComponent extends Upload {

    @Autowired
    private UploadComponentListener uploadComponentListener;

    @Autowired
    private UploadReceiver receiver;

    @PostConstruct
    public void postConstruct() {
        setImmediateMode(true);
        setButtonCaption("select and generate image");
        setReceiver(receiver);
        addStartedListener(uploadComponentListener);
        addProgressListener(uploadComponentListener);
        addSucceededListener(uploadComponentListener);
        addFinishedListener(uploadComponentListener);
    }
}
