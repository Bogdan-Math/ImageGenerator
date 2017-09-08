package layers.web.vaadin.components.visuals;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Slider;
import layers.service.ImageGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class ExpectedColumnsNumberSlider extends Slider {

    @Autowired
    private ImageGenerationService imageGenerationService;

    @PostConstruct
    public void postConstruct() {
        setCaption("Number of expected columns: ");
        setSizeFull();
        setMin(0.0);
        setMax(128.0);
        setValue(64.0);

        int initExpectedColumnsNumber = getValue().intValue();
        imageGenerationService.setExpectedColumnsNumber(initExpectedColumnsNumber);

        addValueChangeListener(event -> {
            int expectedColumnsNumber = getValue().intValue();
            if (expectedColumnsNumber < 8) {
                Notification.show("No, no, no... Trust me, 8 - it's OK!");
                setValue(8.0);
                return;
            }
            imageGenerationService.setExpectedColumnsNumber(expectedColumnsNumber);
        });
    }
}