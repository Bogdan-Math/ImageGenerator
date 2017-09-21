package layers.web.vaadin.component.layout;

import com.vaadin.ui.*;
import domain.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
//TODO: fix value change listeners
public class SliderLayout extends VerticalLayout {

    @Autowired
    private Settings settings;

    @PostConstruct
    public void postConstruct() {
        Label label         = createLabel();
        TextField textField = createTextField();
        Slider slider       = createSlider();

        textField.addValueChangeListener(event -> {
            String textFieldValue = textField.getValue();
            if ("".equals(textFieldValue)) {
                return;
            }
            Integer expectedColumnsNumber = Integer.valueOf(textFieldValue);
            if (expectedColumnsNumber < 8) {
                Notification.show(funnyMessage());
                textField.setValue("8");
                return;
            }
            slider.setValue(expectedColumnsNumber.doubleValue());
            settings.setExpectedColumnsNumber(expectedColumnsNumber);
        });

        slider.addValueChangeListener(event -> {
            Integer expectedColumnsNumber = slider.getValue().intValue();
            if (expectedColumnsNumber < 8) {
                Notification.show(funnyMessage());
                slider.setValue(8.0);
                return;
            }
            textField.setValue(expectedColumnsNumber.toString());
            settings.setExpectedColumnsNumber(expectedColumnsNumber);
        });

        addComponents(label, textField, slider);

        setComponentAlignment(label,     Alignment.MIDDLE_CENTER);
        setComponentAlignment(textField, Alignment.MIDDLE_CENTER);
        setComponentAlignment(slider,    Alignment.MIDDLE_CENTER);
    }

    private Label createLabel() {
        return new Label("Number of expected columns: ");
    }

    private Slider createSlider() {
        Integer columnsNumber = settings.getExpectedColumnsNumber();
        Slider slider = new Slider();
        slider.setSizeFull();
        slider.setMin(0.0);
        slider.setMax(2 * columnsNumber);
        slider.setValue(columnsNumber.doubleValue());
        return slider;
    }

    private TextField createTextField() {
        Integer columnsNumber = settings.getExpectedColumnsNumber();
        TextField textField = new TextField();
        textField.setValue(columnsNumber.toString());
        return textField;
    }

    private String funnyMessage() {
        return "No, no, no... Trust me, 8 - it's OK!";
    }
}
