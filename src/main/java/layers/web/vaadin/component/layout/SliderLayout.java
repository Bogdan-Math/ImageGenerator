package layers.web.vaadin.component.layout;

import com.vaadin.data.Binder;
import com.vaadin.ui.*;
import domain.Settings;
import domain.Settings.ColumnsNumberListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static domain.Settings.MAX_NUMBER_OF_EXPECTED_COLUMNS;
import static domain.Settings.MIN_NUMBER_OF_EXPECTED_COLUMNS;

@Component
@Scope("session")
public class SliderLayout extends HorizontalLayout {

    private static final Integer HINT_NUMBER_OF_EXPECTED_COLUMNS = 8;

    @Autowired
    private Settings settings;

    @PostConstruct
    public void postConstruct() {
        setSizeFull();
        Slider slider   = createSlider();
        TextField field = createField();
        Label label     = createLabel();

        addComponents(label, field, slider);

        setComponentAlignment(label,  Alignment.MIDDLE_CENTER);
        setComponentAlignment(field,  Alignment.MIDDLE_CENTER);
        setComponentAlignment(slider, Alignment.MIDDLE_CENTER);
    }

    private Label createLabel() {

        class ColumnsNumberLabel extends Label implements ColumnsNumberListener {

            private String mainMessage = "Number of expected columns: ";

            private ColumnsNumberLabel() {
                setValue(mainMessage + settings.getExpectedColumnsNumber().toString());
            }

            @Override
            public void changeValueTo(Integer expectedColumnsNumber) {
                setValue(mainMessage + settings.getExpectedColumnsNumber().toString());
            }
        }

        ColumnsNumberLabel label = new ColumnsNumberLabel();
        settings.addColumnsNumberListener(label);
        return label;
    }

    private TextField createField() {

        class Text extends TextField implements ColumnsNumberListener {

            private Text() {
                Binder<Settings> binder = new Binder<>();
                binder.forField(this)
                        .withValidator(string -> string != null && !string.isEmpty(), "Input values should NOT be empty!")
                        .withValidator(string -> {
                            try {
                                Integer value = Integer.valueOf(string);
                                return value >= MIN_NUMBER_OF_EXPECTED_COLUMNS && value <= MAX_NUMBER_OF_EXPECTED_COLUMNS;
                            } catch (NumberFormatException ignored) {
                                return false;
                            }
                        }, "Input value should be a NUMBER from " + MIN_NUMBER_OF_EXPECTED_COLUMNS + " to " + MAX_NUMBER_OF_EXPECTED_COLUMNS)
                        .bind(settings -> settings.getExpectedColumnsNumber().toString(),
                             (settings, value) -> {
                                 settings.setExpectedColumnsNumber(Integer.valueOf(value));
                                 settings.notifyColumnsNumberListeners();
                             });
                binder.setBean(settings);
            }

            @Override
            public void changeValueTo(Integer expectedColumnsNumber) {
                setValue(expectedColumnsNumber.toString());
            }
        }

        Text expectedColumnsNumberField = new Text();
        settings.addColumnsNumberListener(expectedColumnsNumberField);
        return expectedColumnsNumberField;
    }

    private Slider createSlider() {

        class ColumnsSlider extends Slider implements ColumnsNumberListener {

            private ColumnsSlider() {
                setSizeFull();
                setMin(MIN_NUMBER_OF_EXPECTED_COLUMNS);
                setMax(MAX_NUMBER_OF_EXPECTED_COLUMNS);
                setValue(settings.getExpectedColumnsNumber().doubleValue());
                addValueChangeListener(event -> {
                    Integer expectedColumnsNumber = getValue().intValue();
                    if (expectedColumnsNumber < HINT_NUMBER_OF_EXPECTED_COLUMNS) {
                        Notification.show(funnyMessage());
                        setValue(HINT_NUMBER_OF_EXPECTED_COLUMNS.doubleValue());
                        return;
                    }
//                    setCaption(sliderCaption() + expectedColumnsNumber);
                    settings.setExpectedColumnsNumber(expectedColumnsNumber);
                    settings.notifyColumnsNumberListeners();
                });
            }

            @Override
            public void changeValueTo(Integer expectedColumnsNumber) {
                setValue(expectedColumnsNumber.doubleValue());
            }
        }

        ColumnsSlider slider = new ColumnsSlider();
        settings.addColumnsNumberListener(slider);
        return slider;
    }

    private String funnyMessage() {
        return "No, no, no... Trust me, " + HINT_NUMBER_OF_EXPECTED_COLUMNS + " - it's OK!";
    }
}
