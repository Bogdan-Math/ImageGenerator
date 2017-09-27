package layers.web.vaadin.component.visual;

import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class GenerateButton extends Button {

    @PostConstruct
    public void postConstruct() {
        setCaption("generate");
        addClickListener((ClickListener) event -> {
            Notification.show("!!!!!");
        });
    }
}
