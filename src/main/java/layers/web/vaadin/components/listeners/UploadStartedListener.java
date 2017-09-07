package layers.web.vaadin.components.listeners;

import com.vaadin.ui.Upload;

public interface UploadStartedListener extends Upload.StartedListener {

    Upload getUpload();
}