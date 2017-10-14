package layers.web.vaadin.component.layout.slider.listener;

import com.vaadin.ui.Label;
import layers.web.vaadin.component.layout.slider.publisher.ColumnsCountPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class ExpectedColumnsCountLabel extends Label implements ColumnsCountListener {

    @Autowired
    private ColumnsCountPublisher columnsCountPublisher;

    @PostConstruct
    public void postConstruct() {
        columnsCountPublisher.addColumnsCountListener(this);
    }

    @Override
    public void changeValueTo(Integer newValue) {
        String mainMessage = "Expected columns count: ";
        setValue(mainMessage + newValue.toString());
    }
}
