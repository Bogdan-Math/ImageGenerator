package layer.web.vaadin.layout.patterns.group;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.RadioButtonGroup;
import core.Settings;
import domain.InformationalColor;
import domain.InformationalImage;
import domain.PatternType;
import layer.service.PatternImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vaadin.ui.themes.ValoTheme.OPTIONGROUP_HORIZONTAL;
import static domain.PatternType.FLAGS;
import static domain.PatternType.valueOf;

@SpringComponent
@Scope("session")
public class PatternsGroup extends RadioButtonGroup<String> {

    @Autowired
    private Settings settings;

    @Autowired
    private PatternImageService patternImageService;

    @PostConstruct
    public void postConstruct() {
        setItems(Arrays.stream(PatternType.values()).map(Enum::name).collect(Collectors.toList()));
        setValue(FLAGS.name());
        addStyleName(OPTIONGROUP_HORIZONTAL);

        Map<PatternType, Map<InformationalColor, InformationalImage>> patterns = patternImageService.getInformationalMaps();

        settings.setPatterns(patterns.get(valueOf(getValue())));

        addValueChangeListener(event -> settings.setPatterns(patterns.get(valueOf(getValue()))));
    }
}
