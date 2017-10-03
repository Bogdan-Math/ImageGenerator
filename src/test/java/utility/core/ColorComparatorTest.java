package utility.core;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static utility.core.ColorComparator.almostIdentical;

public class ColorComparatorTest {

    @Before
    public void setUp() throws Exception {
        new ColorComparator();
    }

    @Test
    public void almostIdenticalTrue() throws Exception {
        assertTrue(almostIdentical(new Color(0,0,0), new Color(84,84,84)));
    }

    @Test
    public void almostIdenticalFalse() throws Exception {
        assertFalse(almostIdentical(new Color(0,0,0), new Color(85,85,85)));
    }

}