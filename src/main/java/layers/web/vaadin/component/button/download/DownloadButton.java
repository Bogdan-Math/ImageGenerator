package layers.web.vaadin.component.button.download;

import com.vaadin.ui.Button;
import layers.web.vaadin.component.button.download.listener.Downloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class DownloadButton extends Button {

    @Autowired
    private Downloader downloader;

    @PostConstruct
    public void postConstruct() {
        setCaption("download image");
        downloader.extend(this);
    }
}
