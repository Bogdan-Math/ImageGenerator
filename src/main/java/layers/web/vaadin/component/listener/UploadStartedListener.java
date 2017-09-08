package layers.web.vaadin.component.listener;

import com.vaadin.ui.Upload;

public interface UploadStartedListener extends Upload.StartedListener {

    Upload getUpload();
}