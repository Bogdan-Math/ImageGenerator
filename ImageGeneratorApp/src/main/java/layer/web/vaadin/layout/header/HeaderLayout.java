package layer.web.vaadin.layout.header;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

import static layer.web.vaadin.additional.Reference.CODACY;
import static layer.web.vaadin.additional.Reference.GITHUB;

@SpringComponent
@Scope("session")
public class HeaderLayout extends HorizontalLayout {

    public static final String CODACY_LINK_ID = "codacy-link-id";
    public static final String GITHUB_LINK_ID = "github-link-id";

    private Link codacyLink;

    private Link githubLink;

    @PostConstruct
    public void postConstruct() {

        codacyLink = CODACY.link();
        githubLink = GITHUB.link();

        codacyLink.setId(CODACY_LINK_ID);
        githubLink.setId(GITHUB_LINK_ID);

        addComponents(codacyLink, githubLink);

        setComponentAlignment(codacyLink, Alignment.TOP_LEFT);
        setComponentAlignment(githubLink, Alignment.TOP_RIGHT);
    }
}
