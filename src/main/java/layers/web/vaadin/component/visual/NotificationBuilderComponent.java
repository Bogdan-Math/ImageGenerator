package layers.web.vaadin.component.visual;

import com.vaadin.ui.Notification;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Component
@Scope("session")
public class NotificationBuilderComponent implements NotificationBuilder {

    private List<String> notifications;

    @PostConstruct
    public void postConstruct() {
        this.notifications = new LinkedList<>();
    }

    @Override
    public void add(String notification) {
        notifications.add(notification);
    }

    @Override
    public void show() {
        Notification.show(notifications.stream()
                        .map(notification -> "- " + notification)
                        .collect(joining("\n")),
                Notification.Type.TRAY_NOTIFICATION);
        notifications.clear();
    }
}
