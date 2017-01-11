import org.junit.Test;

import static org.junit.Assert.*;

public class CreatorTest {

    //"AAA" Pattern.
    @Test
    public void testNameIs() throws Exception {

        //"A" - Arrange
        String creatorName = "Bogdan";
        String expectedDescription = "Hello, I'm Bogdan - creator of ImageGenerator!";

        //"A" - Act
        Creator creator = new Creator(creatorName);

        //"A" - Assert
        assertEquals(expectedDescription, creator.getDescription());
    }

}