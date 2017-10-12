package layers.web.vaadin.component.layout.slider.listener;

import com.vaadin.ui.Slider;
import layers.web.vaadin.component.layout.slider.publisher.ColumnsNumberPublisher;
import layers.web.vaadin.component.visual.NotificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.vaadin.ui.Notification.Type.HUMANIZED_MESSAGE;
import static domain.Settings.MAX_NUMBER_OF_EXPECTED_COLUMNS;
import static domain.Settings.MIN_NUMBER_OF_EXPECTED_COLUMNS;

@Component
@Scope("session")
public class ExpectedColumnsNumberSlider extends Slider implements ColumnsNumberListener {

    @Autowired
    private ColumnsNumberPublisher columnsNumberPublisher;

    @Autowired
    private NotificationBuilder notificationBuilder;

    @PostConstruct
    public void postConstruct() {
        columnsNumberPublisher.addColumnsNumberListener(this);

        setSizeFull();
        setMin(MIN_NUMBER_OF_EXPECTED_COLUMNS);
        setMax(MAX_NUMBER_OF_EXPECTED_COLUMNS);
        addValueChangeListener(event -> {

            //Check HINT value
            Integer expectedColumnsNumber = event.getValue().intValue();
            if (expectedColumnsNumber < HINT_NUMBER_OF_EXPECTED_COLUMNS) {
                setValue(HINT_NUMBER_OF_EXPECTED_COLUMNS.doubleValue());
                notificationBuilder.add(HINT_COLUMNS_NUMBER_MESSAGE);
                notificationBuilder.showAsHtml(HUMANIZED_MESSAGE);
                return;
            }

            // publish newValue
            columnsNumberPublisher.publishNewValue(expectedColumnsNumber);
        });
    }

    @Override
    public void changeValueTo(Integer newValue) {
        setValue(newValue.doubleValue());
    }
}