package layers.web.vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.themes.ValoTheme;
import layers.service.ImageGenerator;
import domain.PatternType;
import layers.service.PatternsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static domain.PatternType.FLAGS;
import static domain.PatternType.valueOf;

@SpringComponent
@Scope("session")
public class RadioButtonPatternsGroup extends RadioButtonGroup<String> {

    @Autowired
    private ImageGenerator imageGenerator;

    @Autowired
    private PatternsService patternsService;

    @PostConstruct
    public void postConstruct() {
        setItems(Arrays.stream(PatternType.values()).map(Enum::name).collect(Collectors.toList()));
        setValue(FLAGS.name());
        addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        Map<PatternType, Map<Color, BufferedImage>> patterns = patternsService.getAllPatterns();

        imageGenerator.setPatterns(patterns.get(valueOf(getValue())));

        addValueChangeListener(event -> imageGenerator.setPatterns(patterns.get(valueOf(getValue()))));
    }
}
