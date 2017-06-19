package layers.web.vaadin;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.themes.ValoTheme;
import layers.service.ImageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@SpringComponent
@Scope("session")
public class RadioButtonPatternsGroup extends RadioButtonGroup<String> {

    @Autowired
    private ImageGenerator imageGenerator;

    @Resource(name = "patterns")
    private Map<String, String> patterns;

    @PostConstruct
    public void postConstruct() {
        setItems(patterns.keySet());
        setValue(patterns.entrySet().iterator().next().getKey());
        addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
        addValueChangeListener(event -> imageGenerator.setPatternsFrom(patterns.get(getValue())));
    }
}
