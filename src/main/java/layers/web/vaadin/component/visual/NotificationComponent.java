package layers.web.vaadin.component.visual;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("session")
public class NotificationComponent implements Notification {

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
        com.vaadin.ui.Notification.show(notifications.stream()
                        .map(notification -> "- " + notification)
                        .collect(Collectors.joining("\n")),
                com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION);
        notifications.clear();
    }
}
