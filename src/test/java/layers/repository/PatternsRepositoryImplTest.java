package layers.repository;

import org.junit.Before;
import org.junit.Test;
import utility.helper.ObjectTypeConverter;
import utility.helper.ResourceReader;
import utility.pattern.PatternType;

import java.util.HashMap;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static utility.pattern.PatternType.*;

public class PatternsRepositoryImplTest {

    private PatternsRepositoryImpl repository;

    @Before
    public void setUp() throws Exception {
        repository = new PatternsRepositoryImpl();
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
        assertThat(repository.getFlags().size(), is(195));
    }

    @Test
    public void getPlains() throws Exception {
        assertThat(repository.getPlains().size(), is(3));
    }

}