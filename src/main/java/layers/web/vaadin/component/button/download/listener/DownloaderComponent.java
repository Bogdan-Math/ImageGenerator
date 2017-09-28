package layers.web.vaadin.component.button.download.listener;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.GenericFontIcon;
import com.vaadin.server.Resource;
import com.vaadin.ui.AbstractComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class DownloaderComponent extends FileDownloader implements Downloader {

    public DownloaderComponent() {
        super(new GenericFontIcon("", 0));//need to use this bad approach, cause have no idea how to mock required constructor arg
    }

    @Override
    public void extend(AbstractComponent target) {
        super.extend(target);
    }

    @Override
    public void setFileDownloadResource(Resource resource) {
        super.setFileDownloadResource(resource);
    }
}