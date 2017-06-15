package layers.web.vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload;
import org.springframework.context.annotation.Scope;

@SpringComponent
@Scope("prototype")
public class UploadComponent extends Upload {

}
