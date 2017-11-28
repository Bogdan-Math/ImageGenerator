package layers.web.vaadin.layout.slider.listener;

import com.vaadin.ui.TextField;
import layers.web.vaadin.additional.NotificationManager;
import layers.web.vaadin.layout.slider.publisher.ColumnsCountPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.vaadin.ui.Notification.Type.HUMANIZED_MESSAGE;
import static core.Settings.MAX_EXPECTED_COLUMNS_COUNT;
import static core.Settings.MIN_EXPECTED_COLUMNS_COUNT;
import static java.lang.Integer.valueOf;
import static layers.web.vaadin.additional.NotificationManager.showAsHtml;

@Component
@Scope("session")
public class ExpectedColumnsCountField extends TextField implements ColumnsCountListener {

    @Autowired
    private ColumnsCountPublisher columnsCountPublisher;

    @Autowired
    private NotificationManager notificationManager;

    @PostConstruct
    public void postConstruct() {
        columnsCountPublisher.addColumnsCountListener(this);

        addValueChangeListener(event -> {
            String newValue = event.getValue();

            //Check EMPTY value
            if (isNullOrEmpty(newValue)) {
                setValue(event.getOldValue());
                show(EMPTY_COLUMNS_COUNT_MESSAGE);
                return;
            }

            //Check BOUNDS and NUMERIC value
            try {
                Integer newExpectedColumnsCount = valueOf(newValue);
                if (newExpectedColumnsCount < MIN_EXPECTED_COLUMNS_COUNT ||
                        newExpectedColumnsCount > MAX_EXPECTED_COLUMNS_COUNT) {
                    setValue(event.getOldValue());
                    show(BOUNDS_COLUMNS_COUNT_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                setValue(event.getOldValue());
                show(NUMERIC_COLUMNS_COUNT_MESSAGE);
                return;
            }

            //Check HINT value
            Integer expectedColumnsCount = valueOf(event.getValue());
            if (expectedColumnsCount < HINT_EXPECTED_COLUMNS_COUNT) {
                setValue(HINT_EXPECTED_COLUMNS_COUNT.toString());
                show(HINT_COLUMNS_COUNT_MESSAGE);
                return;
            }

            // publish newValue
            columnsCountPublisher.publishNewValue(expectedColumnsCount);
        });
    }

    private void show(String notifications) {
        showAsHtml(notifications, HUMANIZED_MESSAGE);
    }

    @Override
    public void changeValueTo(Integer newValue) {
        setValue(newValue.toString());
    }
}
