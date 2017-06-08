package exceptions;

import basic.ImageGenerator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.image.BufferedImage;

public class MatrixSizeExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testExceptionMessage() throws Exception {

        thrown.expect(MatrixSizeException.class);
        thrown.expectMessage("Number of expected columns (is 10) could not be more than image width (is 1)");

        new ImageGenerator()
                .setImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB))
                .asMatrix(10);
    }
}