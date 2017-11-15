package layers.web.vaadin.layout.patterns;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class PatternsGroupLayoutTest {

    @Autowired
    private PatternsGroupLayout patternsGroupLayout;

    @Test
    public void postConstruct() throws Exception {
        assertNotNull(patternsGroupLayout);
        assertNotNull(patternsGroupLayout.getPatternsGroup());
    }

}