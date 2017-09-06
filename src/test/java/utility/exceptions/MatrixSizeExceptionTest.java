package utility.exceptions;

import layers.service.ImageGenerator;
import layers.service.ImageGeneratorImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import utility.config.ImageGenerationConfig;

import java.awt.image.BufferedImage;

public class MatrixSizeExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exceptionMessage() throws Exception {

        thrown.expect(MatrixSizeException.class);
        thrown.expectMessage("Number of expected columns (is 10) could not be more than image width (is 1)");

        ImageGenerator generator = new ImageGeneratorImpl();
        generator.setConfig(new ImageGenerationConfig(){{
            setImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
        }});
        generator.asMatrix(10);
    }
}