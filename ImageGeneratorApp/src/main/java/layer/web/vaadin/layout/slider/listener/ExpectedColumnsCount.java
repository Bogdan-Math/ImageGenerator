package layer.web.vaadin.layout.slider.listener;

import core.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class ExpectedColumnsCount implements ColumnsCountListener {

    @Autowired
    private Settings settings;

    @Override
    public void changeValueTo(Integer newValue) {
        settings.setExpectedColumnsCount(newValue);
    }
}