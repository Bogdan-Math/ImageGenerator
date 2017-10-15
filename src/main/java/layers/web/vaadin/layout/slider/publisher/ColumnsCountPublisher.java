package layers.web.vaadin.layout.slider.publisher;

import layers.web.vaadin.layout.slider.listener.ColumnsCountListener;

public interface ColumnsCountPublisher {
    void publishNewValue(Integer newValue);
    void addColumnsCountListener(ColumnsCountListener listener);
}
