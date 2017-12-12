package domain;

import org.junit.Test;

import static domain.PatternType.*;
import static org.junit.Assert.assertEquals;

public class PatternTypeTest {

    @Test
    public void getCommonsLocation() {
        assertEquals("images/colors", COMMONS.getLocation());
    }

    @Test
    public void getFlagsLocation() {
        assertEquals("images/flags", FLAGS.getLocation());
    }

    @Test
    public void getPlainsLocation() {
        assertEquals("images/plains", PLAINS.getLocation());
    }

}