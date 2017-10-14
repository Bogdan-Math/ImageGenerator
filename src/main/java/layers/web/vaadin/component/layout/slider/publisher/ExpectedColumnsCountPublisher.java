package layers.web.vaadin.component.layout.slider.publisher;

import layers.web.vaadin.component.layout.slider.listener.ColumnsCountListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Component
@Scope("session")
public class ExpectedColumnsCountPublisher implements ColumnsCountPublisher {

    private List<ColumnsCountListener> listeners;

    @PostConstruct
    public void postConstruct() {
        listeners = new LinkedList<>();
    }

    @Override
    public void publishNewValue(Integer newValue) {
        listeners.forEach(listener -> listener.changeValueTo(newValue));
    }

    @Override
    public void addColumnsCountListener(ColumnsCountListener listener) {
        listeners.add(listener);
    }
}
