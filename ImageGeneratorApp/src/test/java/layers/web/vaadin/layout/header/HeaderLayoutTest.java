package layers.web.vaadin.layout.header;

import com.vaadin.ui.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.vaadin.ui.Alignment.TOP_LEFT;
import static com.vaadin.ui.Alignment.TOP_RIGHT;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class HeaderLayoutTest {

    @Autowired
    private HeaderLayout headerLayout;

    @Test
    public void postConstruct() throws Exception {
        Link codacyLink = headerLayout.getCodacyLink();
        Link githubLink = headerLayout.getGithubLink();

        assertNotNull(headerLayout);
        assertThat(headerLayout.getComponentCount(), is(2));
        assertThat(codacyLink.getId(), is(notNullValue()));
        assertThat(githubLink.getId(), is(notNullValue()));
        assertEquals(TOP_LEFT,  headerLayout.getComponentAlignment(codacyLink));
        assertEquals(TOP_RIGHT, headerLayout.getComponentAlignment(githubLink));
    }

}