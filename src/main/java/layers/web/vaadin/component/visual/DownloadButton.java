package layers.web.vaadin.component.visual;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class DownloadButton extends Button {

    @PostConstruct
    public void postConstruct() {
        setCaption("download image");
    }

    public void download(Resource resource) {
        FileDownloader downloader = new FileDownloader(resource);
        downloader.extend(this);
    }
}
