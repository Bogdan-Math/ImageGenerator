package layers.web.vaadin.additional;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import static com.vaadin.server.Page.getCurrent;

public interface NotificationManager {

    static void showAsString(String notifications, Type type) {
        Notification.show(notifications, type);
    }

    static void showAsHtml(String notifications, Type type) {
        new Notification(notifications, "", type, true).show(getCurrent());
    }

    NotificationManager add(String notification);
    String build();
}
