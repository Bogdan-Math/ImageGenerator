package layers.web.vaadin.component.layout.slider.publisher;

import layers.web.vaadin.component.layout.slider.listener.ColumnsCountListener;

public interface ColumnsCountPublisher {
    void publishNewValue(Integer newValue);
    void addColumnsCountListener(ColumnsCountListener listener);
}
