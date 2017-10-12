package layers.web.vaadin.component.visual;

import com.vaadin.ui.Notification.Type;

public interface NotificationBuilder {
    void add(String notification);
    void showAsString(Type type);
    void showAsHtml(Type type);
}
