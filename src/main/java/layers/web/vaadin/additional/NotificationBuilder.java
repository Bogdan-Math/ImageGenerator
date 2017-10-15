package layers.web.vaadin.additional;

import com.vaadin.ui.Notification.Type;

public interface NotificationBuilder {
    void add(String notification);
    void showAsString(Type type);
    void showAsHtml(Type type);
}
