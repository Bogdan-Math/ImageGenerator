package layers.web.vaadin.component.layout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import lombok.Getter;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static layers.web.vaadin.component.visual.Reference.FLAGS;
import static layers.web.vaadin.component.visual.Reference.JAVARUSH;

@SpringComponent
@Scope("session")
public class FooterLayout extends HorizontalLayout {

    @Getter
    private Link javarushLink;

    @Getter
    private Link flagsLink;

    @PostConstruct
    public void postConstruct() {

        javarushLink = JAVARUSH.link();
        flagsLink = FLAGS.link();

        javarushLink.setId("javarush-link-id");
        flagsLink.setId("flags-link-id");

        javarushLink.setTargetName("_blank");
        flagsLink.setTargetName("_blank");

        HorizontalLayout footerLinksLayout = footerLinksLayout(javarushLink, flagsLink);

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
