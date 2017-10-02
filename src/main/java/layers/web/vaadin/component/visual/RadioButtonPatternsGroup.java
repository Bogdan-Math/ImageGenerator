package layers.web.vaadin.component.visual;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.RadioButtonGroup;
import domain.Settings;
import layers.service.CachedPatternsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import utility.core.InformationalImage;
import utility.core.PatternType;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vaadin.ui.themes.ValoTheme.OPTIONGROUP_HORIZONTAL;
import static utility.core.PatternType.FLAGS;
import static utility.core.PatternType.valueOf;

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
        addStyleName(OPTIONGROUP_HORIZONTAL);

        Map<PatternType, Map<Color, InformationalImage>> patterns = cachedPatternsService.getAllPatterns();

        settings.setPatterns(patterns.get(valueOf(getValue())));

        addValueChangeListener(event -> settings.setPatterns(patterns.get(valueOf(getValue()))));
    }
}
