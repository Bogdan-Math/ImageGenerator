package layers.web.vaadin.component.layout;

import com.vaadin.ui.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@ContextConfiguration(locations = {
        "classpath:spring/footer-layout.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class FooterLayoutTest {

    @Autowired
    private FooterLayout footerLayout;

    @Test
    public void postConstruct() throws Exception {
        Link javarushLink = footerLayout.getJavarushLink();
        Link flagsLink    = footerLayout.getFlagsLink();

        assertNotNull(footerLayout);
        assertThat(javarushLink.getId(), is("javarush-link-id"));
        assertThat(flagsLink.getId(), is("flags-link-id"));
    }

}