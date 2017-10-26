package layers.web.vaadin.additional;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;

public enum Reference {

    CODACY {
        @Override
        public Link link() {
            Link codacyLink = link("https://www.codacy.com/app/bogdan-math-stepanov/ImageGenerator/dashboard");
            codacyLink.setIcon(new ExternalResource("https://api.codacy.com/project/badge/Grade/2d591610b19340738a0bc4b26a330762"));
            return codacyLink;
        }
    },

    GITHUB {
        @Override
        public Link link() {
            Link githubLink = link("https://github.com/Bogdan-Math/ImageGenerator");
            githubLink.setIcon(new ExternalResource("https://camo.githubusercontent.com/a6677b08c955af8400f44c6298f40e7d19cc5b2d/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f677261795f3664366436642e706e67"));
            githubLink.setStyleName("github-link");
            return githubLink;
        }
    },

    JAVARUSH {
        @Override
        public Link link() {
            Link javaRushLink = link("https://javarush.ru");
            javaRushLink.setCaption("Thanks for SKILLS!");
            return javaRushLink;
        }
    },

    FLAGS {
        @Override
        public Link link() {
            Link flagsLink = link("http://www.icondrawer.com");
            flagsLink.setCaption("Thanks for FLAGS!");
            return flagsLink;
        }
    };

    public abstract Link link();

    protected Link link(String sourceURL) {
        Link link = new Link();
        ExternalResource resource = new ExternalResource(sourceURL);

        link.setResource(resource);

        return link;
    }

}
