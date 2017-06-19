package layers.web.vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class UploadComponent extends Upload {

    @Autowired
    private UploadComponentListener uploadComponentListener;

    @PostConstruct
    public void postConstruct() {
        setImmediateMode(true);
        setButtonCaption("select and generate image");
        setReceiver(uploadComponentListener);
        addStartedListener(uploadComponentListener);
        addProgressListener(uploadComponentListener);
        addSucceededListener(uploadComponentListener);
        addFinishedListener(uploadComponentListener);
    }

    public UploadComponentListener getUploadComponentListener() {
        return uploadComponentListener;
    }
}
