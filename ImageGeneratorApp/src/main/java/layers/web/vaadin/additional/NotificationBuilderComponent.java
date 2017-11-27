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
public class NotificationBuilderComponent implements NotificationBuilder {

    private List<String> notifications;
    private String message;

    @PostConstruct
    public void postConstruct() {
        this.notifications = new LinkedList<>();
        this.message       = "";
    }

    @Override
    public NotificationBuilder add(String notification) {
        notifications.add(notification);
        return this;
    }

    @Override
    public NotificationBuilder build() {
        this.message = notifications.stream()
                                    .map(notification -> "- " + notification)
                                    .collect(joining("\n"));
        return this;
    }

    @Override
    public void showAsString(Type type) {
        Notification.show(message, type);
        notifications.clear();
    }

    @Override
    public void showAsHtml(Type type) {
        new Notification(message, "", type, true).show(getCurrent());
        notifications.clear();
    }

    //TODO: add test and use it there
    public List<String> getNotifications() {
        return notifications;
    }

    //TODO: add test and use it there
    public String getMessage() {
        return message;
    }
}