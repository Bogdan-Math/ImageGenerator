package layers.web.vaadin.additional;

import com.vaadin.ui.Notification.Type;

public interface NotificationBuilder {
    NotificationBuilder add(String notification);
    NotificationBuilder build();
    void showAsString(Type type);
    void showAsHtml(Type type);
}
