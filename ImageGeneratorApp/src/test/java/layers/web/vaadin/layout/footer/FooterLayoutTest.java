package layers.web.vaadin.layout.footer;

import com.vaadin.ui.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class FooterLayoutTest {

    @Autowired
    private FooterLayout footerLayout;

    @Test
    public void postConstruct() throws Exception {
        Link javarushLink = footerLayout.getJavarushLink();
        Link flagsLink    = footerLayout.getFlagsLink();

        assertThat(footerLayout, is(notNullValue()));
        assertThat(javarushLink.getId(), is(notNullValue()));
        assertThat(flagsLink.getId(), is(notNullValue()));
    }

}