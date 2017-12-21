package layer.repository;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class PatternRepositoryTest {

    @Autowired
    private PatternRepository repository;

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
    }

    @Test
    public void getCommons() throws Exception {
        long three = 3;
        assertThat(repository.getCommons().count(), is(three));
    }

    @Test
    public void getFlags() throws Exception {
        long two = 2;
        assertThat(repository.getFlags().count(), is(two));
    }

    @Test
    public void getPlains() throws Exception {
        long one = 1;
        assertThat(repository.getPlains().count(), is(one));
    }
}