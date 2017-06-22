package layers.web.vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.themes.ValoTheme;
import layers.service.ImageGenerator;
import layers.service.Patterns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;

@SpringComponent
@Scope("session")
public class RadioButtonPatternsGroup extends RadioButtonGroup<String> {

    @Autowired
    private ImageGenerator imageGenerator;

    @Resource(name = "patterns")
    private Map<Patterns, String> patterns;

    @PostConstruct
    public void postConstruct() {
        setItems(patterns.keySet().stream().map(Enum::name).collect(Collectors.toList()));
        setValue(Patterns.FLAGS.name());
        addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);

        imageGenerator.setPatternsFrom(patterns.get(Patterns.valueOf(getValue())));

        addValueChangeListener(event -> imageGenerator.setPatternsFrom(patterns.get(Patterns.valueOf(getValue()))));
    }
}
