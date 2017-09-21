package layers.web.vaadin.component.layout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import layers.web.vaadin.component.visual.LinksStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class FooterLayout extends VerticalLayout {

    @Autowired
    private LinksStorage linksStorage;

    @PostConstruct
    public void postConstruct() {

        Link flagsLink = linksStorage.flagsLink();

        addComponents(flagsLink);

        setComponentAlignment(flagsLink, Alignment.BOTTOM_RIGHT);
    }
}
