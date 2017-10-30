package domain;

import org.junit.Test;

import static domain.PatternType.*;
import static org.junit.Assert.assertEquals;

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