package layers.web.vaadin.component.layout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import layers.web.vaadin.component.visual.LinksStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class FooterLayout extends HorizontalLayout {

    @Autowired
    private LinksStorage linksStorage;

    @PostConstruct
    public void postConstruct() {

        HorizontalLayout footerLinksLayout = footerLinksLayout(linksStorage.javaRushLink(),
                                                               linksStorage.flagsLink());
        addComponents(footerLinksLayout);

        setComponentAlignment(footerLinksLayout, Alignment.MIDDLE_CENTER);
        setSizeFull();
    }

    private HorizontalLayout footerLinksLayout(Component... components) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(components);
        return horizontalLayout;
    }
}
