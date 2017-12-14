package layer.web.vaadin.layout.footer;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import lombok.Getter;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static layer.web.vaadin.additional.Reference.FLAGS;
import static layer.web.vaadin.additional.Reference.JAVARUSH;

@SpringComponent
@Scope("session")
public class FooterLayout extends HorizontalLayout {

    public static final String JAVARUSH_LINK_ID = "javarush-link-id";
    public static final String FLAGS_LINK_ID    = "flags-link-id";

    @Getter
    private Link javarushLink;

    @Getter
    private Link flagsLink;

    @PostConstruct
    public void postConstruct() {

        javarushLink = JAVARUSH.link();
        flagsLink = FLAGS.link();

        javarushLink.setId(JAVARUSH_LINK_ID);
        flagsLink.setId(FLAGS_LINK_ID);

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
