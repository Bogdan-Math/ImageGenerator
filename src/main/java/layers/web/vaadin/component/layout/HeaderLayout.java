package layers.web.vaadin.component.layout;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import lombok.Getter;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static layers.web.vaadin.component.visual.Reference.CODACY;
import static layers.web.vaadin.component.visual.Reference.GITHUB;

@SpringComponent
@Scope("session")
public class HeaderLayout extends HorizontalLayout {

    @Getter
    private Link codacyLink;

    @Getter
    private Link githubLink;

    @PostConstruct
    public void postConstruct() {

        codacyLink = CODACY.link();
        githubLink = GITHUB.link();

        codacyLink.setId("codacy-link-id");
        githubLink.setId("github-link-id");

        codacyLink.setTargetName("_blank");
        githubLink.setTargetName("_blank");

        addComponents(codacyLink, githubLink);

        setComponentAlignment(codacyLink, Alignment.TOP_LEFT);
        setComponentAlignment(githubLink, Alignment.TOP_RIGHT);
    }
}
