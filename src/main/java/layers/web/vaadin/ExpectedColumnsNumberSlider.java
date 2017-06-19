package layers.web.vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Slider;
import layers.service.ImageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class ExpectedColumnsNumberSlider extends Slider {

    @Autowired
    private ImageGenerator imageGenerator;

    @PostConstruct
    public void postConstruct() {
        setCaption("Number of expected columns: ");
        setSizeFull();
        setMin(8.0);
        setMax(128.0);
        setValue(64.0);
        addValueChangeListener(event -> imageGenerator.setExpectedColumnsNumber(getValue().intValue()));
    }
}