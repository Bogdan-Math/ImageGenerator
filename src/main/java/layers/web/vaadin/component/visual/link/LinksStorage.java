package layers.web.vaadin.component.visual.link;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("singleton")
public class LinksStorage {

    private Link flagsLink;
    private Link codacyLink;
    private Link githubLink;

    @PostConstruct
    public void postConstruct() {
        createFlagsLink();
        createCodacyLink();
        createGithubLink();
    }

    private void createFlagsLink() {
        flagsLink = link("http://www.icondrawer.com");
        flagsLink.setCaption("Thanks for FLAGS!");
    }

    private void createCodacyLink() {
        codacyLink = link("https://www.codacy.com/app/bogdan-math-stepanov/ImageGenerator/dashboard");
        codacyLink.setIcon(new ExternalResource("https://api.codacy.com/project/badge/Grade/433e9ca7250d46f2bbd6280b7db5768c"));
    }

    private void createGithubLink() {
        githubLink = link("https://github.com/Bogdan-Math/ImageGenerator");
        githubLink.setIcon(new ExternalResource("https://camo.githubusercontent.com/a6677b08c955af8400f44c6298f40e7d19cc5b2d/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f677261795f3664366436642e706e67"));
        githubLink.setStyleName("github-link");
    }

    public Link flagsLink() {
        return flagsLink;
    }

    public Link codacyLink() {
        return codacyLink;
    }

    public Link githubLink() {
        return githubLink;
    }

    private Link link(String sourceURL) {
        Link link                 = new Link();
        ExternalResource resource = new ExternalResource(sourceURL);

        link.setResource(resource);

        return link;
    }

}
