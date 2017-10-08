package layers.web.vaadin.component.layout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.vaadin.ui.Alignment.TOP_LEFT;
import static com.vaadin.ui.Alignment.TOP_RIGHT;
import static layers.web.vaadin.component.layout.HeaderLayout.CODACY_LINK_ID;
import static layers.web.vaadin.component.layout.HeaderLayout.GITHUB_LINK_ID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@ContextConfiguration(locations = {
        "classpath:spring/header-layout.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class HeaderLayoutTest {

    @Autowired
    private HeaderLayout headerLayout;

    @Test
    public void postConstruct() throws Exception {
        assertNotNull(headerLayout);
        assertThat(headerLayout.getComponentCount(), is(2));
        assertThat(headerLayout.getCodacyLink().getId(), is(CODACY_LINK_ID));
        assertThat(headerLayout.getGithubLink().getId(), is(GITHUB_LINK_ID));
        assertEquals(TOP_LEFT, headerLayout.getComponentAlignment(headerLayout.getCodacyLink()));
        assertEquals(TOP_RIGHT, headerLayout.getComponentAlignment(headerLayout.getGithubLink()));
    }

}