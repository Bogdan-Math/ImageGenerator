package domain;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InformationalColorTest {

    private InformationalColor blackColor;

    @Before
    public void setUp() {
        this.blackColor = new InformationalColor(0, 0, 0);
    }

    @Test
    public void almostEqualIsTrue() {
        random100AllowedColors()
                .forEach(color -> assertTrue(blackColor.almostEqualTo(color)));
        assertTrue(blackColor.almostEqualTo(new InformationalColor(84,84,84)));
    }

    @Test
    public void almostEqualIsFalse() {
        random100DenyColors()
                .forEach(color -> assertFalse(blackColor.almostEqualTo(color)));
        assertFalse(blackColor.almostEqualTo(new InformationalColor(85,85,85)));
    }

    private List<InformationalColor> random100AllowedColors() {
        return randomColors(0, 85);
    }

    private List<InformationalColor> random100DenyColors() {
        return randomColors(85, 256);
    }

    private List<InformationalColor> randomColors(Integer minComponentValue, Integer maxComponentValue) {
        Random random = new Random();
        return range(0, 100).mapToObj(count -> {
            int[] ints = random.ints(minComponentValue, maxComponentValue).limit(3).toArray();
            return new InformationalColor(ints[0], ints[1], ints[2]);
        }).collect(toList());
    }

}