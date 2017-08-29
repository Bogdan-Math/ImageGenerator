package layers.web.vaadin.listeners;

import com.vaadin.ui.Upload;

public interface UploadStartedListener extends Upload.StartedListener {

    Upload getUpload();
}