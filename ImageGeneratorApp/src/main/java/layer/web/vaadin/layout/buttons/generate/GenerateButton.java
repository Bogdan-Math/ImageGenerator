package layer.web.vaadin.layout.buttons.generate;

import com.vaadin.ui.Button;
import layer.web.vaadin.layout.buttons.generate.listener.GenerateClickListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class GenerateButton extends Button {

    private static final String GENERATE_BUTTON_ID = "generate-button-id";
    private static final String GENERATE           = "generate";

    @Autowired
    private GenerateClickListener clickListener;

    @PostConstruct
    public void postConstruct() {
        setId(GENERATE_BUTTON_ID);
        setCaption(GENERATE);
        addClickListener(clickListener);
    }
}
