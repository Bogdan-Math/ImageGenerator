package layers.web.vaadin.component.visual;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.GenericFontIcon;
import com.vaadin.ui.Button;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class DownloadButton extends Button {

    private FileDownloader fileDownloader;

    @PostConstruct
    public void postConstruct() {
        setCaption("download image");
        setEnabled(false);

        fileDownloader = createFileDownloader();
        fileDownloader.extend(this);
    }

    public FileDownloader getFileDownloader() {
        return fileDownloader;
    }

    private FileDownloader createFileDownloader() {
        //TODO: try to find better approach for creation FileDownloader instance
        return new FileDownloader(new GenericFontIcon("", 0));
    }
}
