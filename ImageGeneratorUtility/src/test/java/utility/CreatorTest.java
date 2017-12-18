package utility;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreatorTest {

    //"AAA" Pattern.
    @Test
    public void description() {

        //"A" - Arrange
        String creatorName = "Bogdan";
        String expectedDescription = "Hello, I'm Bogdan - creator of ImageGenerator!";

        //"A" - Act
        Creator creator = new Creator(creatorName);

        //"A" - Assert
        assertThat(creator.getDescription(), equalTo(expectedDescription));
    }

}