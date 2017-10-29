package layers.web.vaadin.additional;

import com.vaadin.ui.Link;
import org.junit.Test;

import static layers.web.vaadin.additional.Reference.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReferenceTest {

    @Test
    public void codacy() throws Exception {
        Link codacyLink = CODACY.link();

        assertThat(codacyLink.getCaption(), is((String) null));
    }

    @Test
    public void github() throws Exception {
        Link githubLink = GITHUB.link();

        assertThat(githubLink.getCaption(), is((String) null));
    }

    @Test
    public void flags() throws Exception {
        Link flagsLink = FLAGS.link();

        assertThat(flagsLink.getCaption(), is("Thanks for FLAGS!"));
    }

    @Test
    public void javarush() throws Exception {
        Link javarushLink = JAVARUSH.link();

        assertThat(javarushLink.getCaption(), is("Thanks for SKILLS!"));
    }

}