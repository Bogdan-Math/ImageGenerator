package layers.web.vaadin.component.layout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static layers.web.vaadin.component.visual.Reference.CODACY;
import static layers.web.vaadin.component.visual.Reference.GITHUB;

@SpringComponent
@Scope("session")
public class HeaderLayout extends HorizontalLayout {

    @PostConstruct
    public void postConstruct() {

        Link codacyLink = CODACY.link();
        Link githubLink = GITHUB.link();

        addComponents(codacyLink, githubLink);

        setComponentAlignment(codacyLink, Alignment.TOP_LEFT);
        setComponentAlignment(githubLink, Alignment.TOP_RIGHT);
    }
}
