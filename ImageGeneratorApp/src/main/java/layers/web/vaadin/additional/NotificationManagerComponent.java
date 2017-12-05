package layers.web.vaadin.additional;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

import static com.vaadin.server.Page.getCurrent;
import static java.util.stream.Collectors.joining;

@Component
@Scope("session")
public class NotificationManagerComponent implements NotificationManager {

    private List<String> notifications;

    @PostConstruct
    public void postConstruct() {
        this.notifications = new LinkedList<>();
    }

    @Override
    public NotificationManager add(String notification) {
        notifications.add(notification);
        return this;
    }

    @Override
    public void showAs(Type type) {
        String message = notifications.stream()
                .map(notification -> "- " + notification)
                .collect(joining("<br>"));//need to separate lines

        new Notification(message, "", type, true).show(getCurrent());
        notifications.clear();
    }
}