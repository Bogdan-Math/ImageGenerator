package layers.web.vaadin.component.layout;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;
import domain.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static domain.Settings.*;

@Component
@Scope("session")
public class SliderLayout extends VerticalLayout {

    private static final Integer HINT_NUMBER_OF_EXPECTED_COLUMNS = 8;

    @Autowired
    private Settings settings;

    @PostConstruct
    public void postConstruct() {
        Slider slider = createSlider();

        addComponents(slider);

        setComponentAlignment(slider, Alignment.MIDDLE_CENTER);
    }

    private Slider createSlider() {
        Integer columnsNumber = settings.getExpectedColumnsNumber();
        Slider slider = new Slider();
        slider.setCaption(sliderCaption() + columnsNumber);
        slider.setSizeFull();
        slider.setMin(MIN_NUMBER_OF_EXPECTED_COLUMNS);
        slider.setMax(MAX_NUMBER_OF_EXPECTED_COLUMNS);
        slider.setValue(columnsNumber.doubleValue());

        slider.addValueChangeListener(event -> {
            Integer expectedColumnsNumber = slider.getValue().intValue();
            if (expectedColumnsNumber < HINT_NUMBER_OF_EXPECTED_COLUMNS) {
                Notification.show(funnyMessage());
                slider.setValue(HINT_NUMBER_OF_EXPECTED_COLUMNS.doubleValue());
                return;
            }
            slider.setCaption(sliderCaption() + expectedColumnsNumber);
            settings.setExpectedColumnsNumber(expectedColumnsNumber);
        });

        return slider;
    }

    private String sliderCaption() {
        return "Number of expected columns: ";
    }

    private String funnyMessage() {
        return "No, no, no... Trust me, " + HINT_NUMBER_OF_EXPECTED_COLUMNS + " - it's OK!";
    }
}
