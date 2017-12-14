package layer.web.vaadin.layout.buttons;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import layer.web.vaadin.layout.buttons.download.DownloadButton;
import layer.web.vaadin.layout.buttons.generate.GenerateButton;
import layer.web.vaadin.layout.buttons.upload.UploadButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class ButtonsLayout extends HorizontalLayout {

    @Autowired
    private UploadButton uploadButton;

    @Autowired
    private GenerateButton generateButton;

    @Autowired
    private DownloadButton downloadButton;

    @PostConstruct
    public void postConstruct() {
        setSizeFull();

        addComponents(uploadButton, generateButton, downloadButton);

        setComponentAlignment(uploadButton,   Alignment.MIDDLE_RIGHT);
        setComponentAlignment(generateButton, Alignment.MIDDLE_CENTER);
        setComponentAlignment(downloadButton, Alignment.MIDDLE_LEFT);
    }
}
