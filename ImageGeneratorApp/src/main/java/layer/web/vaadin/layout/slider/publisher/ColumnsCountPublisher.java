package layer.web.vaadin.layout.slider.publisher;

import layer.web.vaadin.layout.slider.listener.ColumnsCountListener;

public interface ColumnsCountPublisher {
    void publishNewValue(Integer newValue);
    ColumnsCountPublisher addColumnsCountListener(ColumnsCountListener listener);
}
