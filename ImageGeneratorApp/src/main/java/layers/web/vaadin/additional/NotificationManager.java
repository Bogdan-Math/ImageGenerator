package layers.web.vaadin.additional;

import com.vaadin.ui.Notification.Type;

public interface NotificationManager {

    NotificationManager add(String notification);
    void showAs(Type type);
}
