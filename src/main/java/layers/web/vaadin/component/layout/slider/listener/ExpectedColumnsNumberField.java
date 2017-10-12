package layers.web.vaadin.component.layout.slider.listener;

import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import layers.web.vaadin.component.layout.slider.publisher.ColumnsNumberPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static domain.Settings.MAX_NUMBER_OF_EXPECTED_COLUMNS;
import static domain.Settings.MIN_NUMBER_OF_EXPECTED_COLUMNS;
import static java.lang.Integer.valueOf;

@Component
@Scope("session")
public class ExpectedColumnsNumberField extends TextField implements ColumnsNumberListener {

    @Autowired
    private ColumnsNumberPublisher columnsNumberPublisher;

    @PostConstruct
    public void postConstruct() {
        columnsNumberPublisher.addColumnsNumberListener(this);

        addValueChangeListener(event -> {
            String newValue = event.getValue();

            //Check EMPTY value
            if (newValue == null || newValue.isEmpty()) {
                setValue(event.getOldValue());
                Notification.show(EMPTY_COLUMNS_NUMBER_MESSAGE);
                return;
            }

            //Check BOUNDS and NUMERIC value
            try {
                Integer newExpectedColumnsNumber = valueOf(newValue);
                if (newExpectedColumnsNumber <= MIN_NUMBER_OF_EXPECTED_COLUMNS ||
                        newExpectedColumnsNumber >= MAX_NUMBER_OF_EXPECTED_COLUMNS) {
                    setValue(event.getOldValue());
                    Notification.show(BOUNDS_COLUMNS_NUMBER_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                setValue(event.getOldValue());
                Notification.show(NUMERIC_COLUMNS_NUMBER_MESSAGE);
                return;
            }

            //Check HINT value
            Integer expectedColumnsNumber = valueOf(event.getValue());
            if (expectedColumnsNumber < HINT_NUMBER_OF_EXPECTED_COLUMNS) {
                setValue(HINT_NUMBER_OF_EXPECTED_COLUMNS.toString());
                Notification.show(HINT_COLUMNS_NUMBER_MESSAGE);
                return;
            }

            // publish newValue
            columnsNumberPublisher.publishNewValue(expectedColumnsNumber);
        });
    }

    @Override
    public void changeValueTo(Integer newValue) {
        setValue(newValue.toString());
    }
}
