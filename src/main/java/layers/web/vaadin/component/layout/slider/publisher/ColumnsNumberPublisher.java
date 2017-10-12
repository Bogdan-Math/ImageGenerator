package layers.web.vaadin.component.layout.slider.publisher;

import layers.web.vaadin.component.layout.slider.listener.ColumnsNumberListener;

public interface ColumnsNumberPublisher {
    void publishNewValue(Integer newValue);
    void addColumnsNumberListener(ColumnsNumberListener listener);
}
