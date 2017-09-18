package layers.web.vaadin.component.visual;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import layers.web.vaadin.component.visual.link.LinksStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class HeaderLayout extends HorizontalLayout {

    @Autowired
    private LinksStorage linksStorage;

    @PostConstruct
    public void postConstruct() {

        Link codacyLink = linksStorage.codacyLink();
        Link githubLink = linksStorage.githubLink();

        addComponents(codacyLink, githubLink);

        setComponentAlignment(codacyLink, Alignment.TOP_LEFT);
        setComponentAlignment(githubLink, Alignment.TOP_RIGHT);
    }
}
