package layers.service;

import layers.repository.BasicPatternsRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import utility.exceptions.MatrixSizeException;
import utility.helpers.ImageInformation;
import utility.helpers.ObjectTypeConverter;
import utility.helpers.ResourceReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ImageGeneratorImplTest {

    @InjectMocks
    private ImageGenerator imageGenerator;

    private ResourceReader resourceReader;
    private ObjectTypeConverter converter;
    private ImageInformation imageInformation;

    private BasicPatternsRepository repository;


    private BufferedImage whiteImage;

    private BufferedImage canonicalImage;
    private Map<Color, BufferedImage> patterns;
    private Integer expectedColumnsNumber;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        this.resourceReader   = new ResourceReader();
        this.converter        = new ObjectTypeConverter();
        this.imageInformation = new ImageInformation();

        this.repository = new BasicPatternsRepository();
        this.repository.setResourceReader(resourceReader);
        this.repository.setConverter(converter);
        this.repository.setImageInformation(imageInformation);

        this.canonicalImage = ImageIO.read(resourceReader.readFile("images/canonical.jpg"));
        this.whiteImage     = ImageIO.read(resourceReader.readFile("images/colors/1-white.jpg"));

        this.expectedColumnsNumber = 200;
        this.patterns              = repository.getFlags();// patternsMap(resourceReader.readFiles("images/flags"));

        this.imageGenerator = new ImageGeneratorImpl()
                .setImageInformation(imageInformation)
                .setImage(canonicalImage)
                .setPatterns(patterns)
                .setExpectedColumnsNumber(expectedColumnsNumber);
    }

    @After
    public void tearDown() throws Exception {
        Optional.ofNullable(resourceReader.readFile("images/").listFiles())
                .ifPresent(filesArr -> Arrays.stream(filesArr)
                        .filter(file -> (file.getName().matches("^generate_image.+")))
                        .forEach(File::delete));
    }

    @Test
    public void asMatrix() throws Exception {
        int expectedColumn = 100;

        List<List<BufferedImage>> matrix = imageGenerator.setImage(canonicalImage).asMatrix(expectedColumn);
        int matrixWidth = matrix.size();
        int matrixHeight = matrix.get(0).size();

        assertTrue(matrixWidth <= canonicalImage.getWidth()); // matrix width CAN NOT be more then image width.
        assertTrue(matrixHeight <= canonicalImage.getHeight()); // matrix height CAN NOT be more then image height.
    }

    @Test
    public void asMatrix_exception() throws Exception {
        int wrongExpectedColumns = 2000;
        thrown.expect(MatrixSizeException.class);
        thrown.expectMessage("Number of expected columns (is 2000) could not be more than image width (is 1600).");

        imageGenerator.setImage(canonicalImage).asMatrix(wrongExpectedColumns);
    }

    @Test
    public void averagedColorsMatrix() throws Exception {
        int white = 255;
        imageGenerator.setImage(whiteImage).setExpectedColumnsNumber(10)
                .averagedColorsMatrix()
                .forEach(row -> row
                        .forEach(averageRGB -> {
                            assertEquals(white, averageRGB.getRed());
                            assertEquals(white, averageRGB.getGreen());
                            assertEquals(white, averageRGB.getBlue());
                        }));
    }

    @Test
    public void resultMatrix() throws Exception {
        imageGenerator.setExpectedColumnsNumber(250)
                .resultMatrix()
                .forEach(row -> row
                        .forEach(patternImg -> {
                            assertTrue(patterns.values().contains(patternImg));
                        }));
    }

    @Test
    public void generateImage() throws Exception {
        BufferedImage generatedImage = imageGenerator.setImage(whiteImage)
                                                     .setExpectedColumnsNumber(10)
                                                     .generateImage();

        assertNotEquals(whiteImage, generatedImage);
        assertTrue(generatedImage.getWidth() >= whiteImage.getWidth());
        assertTrue(generatedImage.getHeight() >= whiteImage.getHeight());
    }
}