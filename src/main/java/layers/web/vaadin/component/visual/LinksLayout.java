package layers.web.vaadin.component.visual;

import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class LinksLayout extends HorizontalLayout {

    @PostConstruct
    public void postConstruct() {

        Link codacyLink = codacyLink();
        Link githubLink = githubLink();

        addComponents(codacyLink, githubLink);

        setComponentAlignment(codacyLink, Alignment.TOP_LEFT);
        setComponentAlignment(githubLink, Alignment.TOP_RIGHT);
    }

    private Link codacyLink() {
        Link codacyLink = link("https://www.codacy.com/app/bogdan-math-stepanov/ImageGenerator/dashboard");
        codacyLink.setIcon(new ExternalResource("https://api.codacy.com/project/badge/Grade/433e9ca7250d46f2bbd6280b7db5768c"));
        return codacyLink;
    }

    private Link githubLink() {
        Link githubLink = link("https://github.com/Bogdan-Math/ImageGenerator");
        githubLink.setIcon(new ExternalResource("https://camo.githubusercontent.com/a6677b08c955af8400f44c6298f40e7d19cc5b2d/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f677261795f3664366436642e706e67"));
        githubLink.setStyleName("github-link");
        return githubLink;
    }

    private Link link(String sourceURL) {
        Link link                 = new Link();
        ExternalResource resource = new ExternalResource(sourceURL);
//        ExternalResource iconLink = new ExternalResource("https://api.codacy.com/project/badge/Grade/433e9ca7250d46f2bbd6280b7db5768c");

        link.setResource(resource);
//        toGithub.setIcon(iconLink);

        return link;
    }

}
