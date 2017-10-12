package layers.web.vaadin.component.layout.slider.listener;

import com.vaadin.ui.Label;
import layers.web.vaadin.component.layout.slider.publisher.ColumnsNumberPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class ExpectedColumnsNumberLabel extends Label implements ColumnsNumberListener {

    @Autowired
    private ColumnsNumberPublisher columnsNumberPublisher;

    @PostConstruct
    public void postConstruct() {
        columnsNumberPublisher.addColumnsNumberListener(this);
    }

    @Override
    public void changeValueTo(Integer newValue) {
        String mainMessage = "Number of expected columns: ";
        setValue(mainMessage + newValue.toString());
    }
}
