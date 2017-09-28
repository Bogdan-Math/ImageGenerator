package layers.web.vaadin.component.button.generate;

import com.vaadin.ui.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class GenerateButton extends Button {

    @Autowired
    private ClickListener clickListener;

    @PostConstruct
    public void postConstruct() {
        setCaption("generate");
        addClickListener(clickListener);
    }
}
