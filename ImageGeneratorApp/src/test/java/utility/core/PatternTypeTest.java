package utility.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static utility.core.PatternType.*;

public class PatternTypeTest {

    @Test
    public void getCommonsLocation() throws Exception {
        assertEquals("images/colors", COMMONS.getLocation());
    }

    @Test
    public void getFlagsLocation() throws Exception {
        assertEquals("images/flags", FLAGS.getLocation());
    }

    @Test
    public void getPlainsLocation() throws Exception {
        assertEquals("images/plains", PLAINS.getLocation());
    }

}