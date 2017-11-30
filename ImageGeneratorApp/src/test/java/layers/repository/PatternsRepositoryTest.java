package layers.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class PatternsRepositoryTest {

    @Autowired
    private PatternsRepository repository;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(null);
    }

    @Test
    public void getCommons() throws Exception {
        assertThat(repository.getCommons().size(), is(3));
    }

    @Test
    public void getFlags() throws Exception {
        assertThat(repository.getFlags().size(), is(2));
    }

    @Test
    public void getPlains() throws Exception {
        assertThat(repository.getPlains().size(), is(1));
    }

    @Test
    public void twoSameAveragedColors() throws Exception {
        repository.getFlags();

        assertThat(outContent.toString(), containsString("Two same averaged colors:"));
    }
}