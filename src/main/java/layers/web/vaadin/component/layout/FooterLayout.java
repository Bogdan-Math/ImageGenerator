package layers.web.vaadin.component.layout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static layers.web.vaadin.component.visual.Reference.FLAGS;
import static layers.web.vaadin.component.visual.Reference.JAVARUSH;

@SpringComponent
@Scope("session")
public class FooterLayout extends HorizontalLayout {

    @PostConstruct
    public void postConstruct() {

        HorizontalLayout footerLinksLayout = footerLinksLayout(JAVARUSH.link(), FLAGS.link());

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