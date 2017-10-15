package layers.web.vaadin.layout.slider;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import domain.Settings;
import layers.web.vaadin.layout.slider.listener.ExpectedColumnsCountField;
import layers.web.vaadin.layout.slider.listener.ExpectedColumnsCountLabel;
import layers.web.vaadin.layout.slider.listener.ExpectedColumnsCountSlider;
import layers.web.vaadin.layout.slider.publisher.ColumnsCountPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class SliderLayout extends HorizontalLayout {

    @Autowired
    private Settings settings;

    @Autowired
    private ExpectedColumnsCountLabel label;

    @Autowired
    private ExpectedColumnsCountSlider slider;

    @Autowired
    private ExpectedColumnsCountField field;

    @Autowired
    private ColumnsCountPublisher columnsCountPublisher;

    @PostConstruct
    public void postConstruct() {
        setSizeFull();

        addComponents(label, slider, field);

        setComponentAlignment(label,  Alignment.MIDDLE_RIGHT);
        setComponentAlignment(slider, Alignment.MIDDLE_CENTER);
        setComponentAlignment(field,  Alignment.MIDDLE_LEFT);

        columnsCountPublisher.publishNewValue(settings.getExpectedColumnsCount());
    }
}
