package layer.web.vaadin.layout.slider.listener;

import com.vaadin.ui.Slider;
import layer.web.vaadin.additional.NotificationManager;
import layer.web.vaadin.layout.slider.publisher.ColumnsCountPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.vaadin.ui.Notification.Type.HUMANIZED_MESSAGE;
import static core.Settings.MAX_EXPECTED_COLUMNS_COUNT;
import static core.Settings.MIN_EXPECTED_COLUMNS_COUNT;

@Component
@Scope("session")
public class ExpectedColumnsCountSlider extends Slider implements ColumnsCountListener {

    @Autowired
    private ColumnsCountPublisher columnsCountPublisher;

    @Autowired
    private NotificationManager notificationManager;

    @PostConstruct
    public void postConstruct() {

        setSizeFull();
        setMin(MIN_EXPECTED_COLUMNS_COUNT);
        setMax(MAX_EXPECTED_COLUMNS_COUNT);
        addValueChangeListener(event -> {

            //Check HINT value
            Integer expectedColumnsNumber = event.getValue().intValue();
            if (expectedColumnsNumber < HINT_EXPECTED_COLUMNS_COUNT) {
                setValue(HINT_EXPECTED_COLUMNS_COUNT.doubleValue());

                notificationManager.add(HINT_COLUMNS_COUNT_MESSAGE)
                                   .showAs(HUMANIZED_MESSAGE);
                return;
            }

            // publish newValue
            columnsCountPublisher.publishNewValue(expectedColumnsNumber);
        });
    }

    @Override
    public void changeValueTo(Integer newValue) {
        setValue(newValue.doubleValue());
    }
}