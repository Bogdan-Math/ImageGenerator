package layer.web.vaadin.layout.slider.listener;

import com.vaadin.ui.Label;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ExpectedColumnsCountLabel extends Label implements ColumnsCountListener {

    @Override
    public void changeValueTo(Integer newValue) {
        setValue("Expected columns count: " + newValue);
    }
}
