package layers.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration(locations = {
        "classpath:spring/patterns-repository.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class PatternsRepositoryImplTest {

    @Autowired
    private PatternsRepository repository;

    @Test
    public void getCommons() throws Exception {
        assertThat(repository.getCommons().size(), is(24));
    }

    @Test
    public void getFlags() throws Exception {
        assertThat(repository.getFlags().size(), is(189));
    }

    @Test
    public void getPlains() throws Exception {
        assertThat(repository.getPlains().size(), is(3));
    }

}