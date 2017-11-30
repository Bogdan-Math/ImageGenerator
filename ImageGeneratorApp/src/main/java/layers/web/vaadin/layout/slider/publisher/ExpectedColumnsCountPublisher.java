package layers.web.vaadin.layout.slider.publisher;

import layers.web.vaadin.layout.slider.listener.ColumnsCountListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Component
@Scope("session")
public class ExpectedColumnsCountPublisher implements ColumnsCountPublisher {

    private Integer oldValue;
    private List<ColumnsCountListener> listeners;

    @PostConstruct
    public void postConstruct() {
        this.oldValue  = 0;
        this.listeners = new LinkedList<>();
    }

    @Override
    public void publishNewValue(Integer newValue) {
        if (!oldValue.equals(newValue)) {
            listeners.forEach(listener -> listener.changeValueTo(newValue));
            oldValue = newValue;
        }
    }

    @Override
    public ColumnsCountPublisher addColumnsCountListener(ColumnsCountListener listener) {
        listeners.add(listener);
        return this;
    }
}
