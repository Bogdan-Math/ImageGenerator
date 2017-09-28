package layers.web.vaadin.component.visual;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.themes.ValoTheme;
import domain.Settings;
import layers.service.CachedPatternsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import utility.pattern.PatternType;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static utility.pattern.PatternType.FLAGS;
import static utility.pattern.PatternType.valueOf;

@SpringComponent
@Scope("session")
public class RadioButtonPatternsGroup extends RadioButtonGroup<String> {

    @Autowired
    private Settings settings;

    @Autowired
    private CachedPatternsService cachedPatternsService;

    @PostConstruct
    public void postConstruct() {
        setItems(Arrays.stream(PatternType.values()).map(Enum::name).collect(Collectors.toList()));
        setValue(FLAGS.name());
        addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        Map<PatternType, Map<Color, BufferedImage>> patterns = cachedPatternsService.getAllPatterns();

        settings.setPatterns(patterns.get(valueOf(getValue())));

        addValueChangeListener(event -> settings.setPatterns(patterns.get(valueOf(getValue()))));
    }
}
