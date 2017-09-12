package utility.exception;

import domain.ImageGenerator;
import domain.BasicImageGenerator;
import domain.BasicSettings;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.awt.image.BufferedImage;

public class MatrixSizeExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exceptionMessage() throws Exception {

        thrown.expect(MatrixSizeException.class);
        thrown.expectMessage("Number of expected columns (is 10) could not be more than image width (is 1)");

        ImageGenerator imageGenerator = new BasicImageGenerator();
        imageGenerator.setSettings(new BasicSettings() {{
            setImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB));
            setExpectedColumnsNumber(10);
        }});
        imageGenerator.asMatrix();
    }
}