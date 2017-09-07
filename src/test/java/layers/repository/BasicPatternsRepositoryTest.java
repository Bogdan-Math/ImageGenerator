package layers.repository;

import domain.PatternType;
import org.junit.Before;
import org.junit.Test;
import utility.helpers.ImageInformation;
import utility.helpers.ObjectTypeConverter;
import utility.helpers.ResourceReader;

import java.util.HashMap;

import static domain.PatternType.COMMONS;
import static domain.PatternType.FLAGS;
import static domain.PatternType.PLAINS;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BasicPatternsRepositoryTest {

    private BasicPatternsRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = new BasicPatternsRepository();
        repository.setResourceReader(new ResourceReader());
        repository.setConverter(new ObjectTypeConverter());
        repository.setPatternsLocation(new HashMap<PatternType, String>() {{
            put(COMMONS, "images/colors");
            put(FLAGS, "images/flags");
            put(PLAINS, "images/plains");
        }});
    }

    @Test
    public void getCommons() throws Exception {
        assertThat(repository.getCommons().size(), is(24));
    }

    @Test
    public void getFlags() throws Exception {
        assertThat(repository.getFlags().size(), is(196));
    }

    @Test
    public void getPlains() throws Exception {
        assertThat(repository.getPlains().size(), is(3));
    }

}