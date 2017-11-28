package layers.web.vaadin.additional;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

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
    public String build() {
        String message = notifications.stream()
                                      .map(notification -> "- " + notification)
                                      .collect(joining("\n"));
        notifications.clear();
        return message;
    }
}