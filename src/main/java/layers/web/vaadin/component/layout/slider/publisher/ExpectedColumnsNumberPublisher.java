package layers.web.vaadin.component.layout.slider.publisher;

import layers.web.vaadin.component.layout.slider.listener.ColumnsNumberListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Component
@Scope("session")
public class ExpectedColumnsNumberPublisher implements ColumnsNumberPublisher {

    private List<ColumnsNumberListener> listeners;

    @PostConstruct
    public void postConstruct() {
        listeners = new LinkedList<>();
    }

    @Override
    public void publishNewValue(Integer newValue) {
        listeners.forEach(listener -> listener.changeValueTo(newValue));
    }

    @Override
    public void addColumnsNumberListener(ColumnsNumberListener listener) {
        listeners.add(listener);
    }
}
