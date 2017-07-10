package layers.web.vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.themes.ValoTheme;
import layers.service.ImageGenerator;
import layers.service.Patterns;
import layers.service.PatternsInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static layers.service.Patterns.FLAGS;
import static layers.service.Patterns.valueOf;

@SpringComponent
@Scope("session")
public class RadioButtonPatternsGroup extends RadioButtonGroup<String> {

    @Autowired
    private ImageGenerator imageGenerator;

    @Autowired
    private PatternsInitializer patternsInitializer;

    @PostConstruct
    public void postConstruct() {
        setItems(Arrays.stream(Patterns.values()).map(Enum::name).collect(Collectors.toList()));
        setValue(FLAGS.name());
        addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        Map<Patterns, Map<Color, BufferedImage>> patterns = patternsInitializer.getPatterns();

        imageGenerator.setPatterns(patterns.get(valueOf(getValue())));

        addValueChangeListener(event -> imageGenerator.setPatterns(patterns.get(valueOf(getValue()))));
    }
}
