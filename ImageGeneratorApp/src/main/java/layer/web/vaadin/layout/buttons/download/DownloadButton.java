package layer.web.vaadin.layout.buttons.download;

import com.vaadin.ui.Button;
import layer.web.vaadin.layout.buttons.download.listener.Downloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class DownloadButton extends Button {

    private static final String DOWNLOAD_BUTTON_ID = "download-button-id";
    private static final String DOWNLOAD_IMAGE     = "download image";

    @Autowired
    private Downloader downloader;

    @PostConstruct
    public void postConstruct() {
        setId(DOWNLOAD_BUTTON_ID);
        setCaption(DOWNLOAD_IMAGE);
        downloader.extend(this);
    }
}
