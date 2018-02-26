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
public class PatternImageRepositoryTest {

    @Autowired
    private PatternImageRepository repository;

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void getCommons() {
        long three = 3;
        assertThat(repository.getCommons().count(), is(three));
    }

    @Test
    public void getFlags() {
        long two = 2;
        assertThat(repository.getFlags().count(), is(two));
    }

    @Test
    public void getPlains() {
        long one = 1;
        assertThat(repository.getPlains().count(), is(one));
    }
}