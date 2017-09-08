package domain;

import domain.ImageGenerator;
import domain.BasicImageGenerator;
import utility.pattern.PatternType;
import layers.repository.PatternsRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import utility.exception.MatrixSizeException;
import utility.helper.ImageInformation;
import utility.helper.ObjectTypeConverter;
import utility.helper.ResourceReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

import static utility.pattern.PatternType.*;
import static org.junit.Assert.*;

public class BasicImageGeneratorTest {

    private ImageGenerator imageGenerator;

    private ImageGeneratorSettings settings;

    private BufferedImage canonicalImage;
    private Map<Color, BufferedImage> patterns;
    private Integer expectedColumnsNumber;

    private ResourceReader resourceReader;
    private BufferedImage whiteImage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {

        this.resourceReader = new ResourceReader();
        this.whiteImage     = ImageIO.read(resourceReader.readFile("images/colors/1-white.jpg"));

        PatternsRepositoryImpl repository = new PatternsRepositoryImpl();
        repository.setResourceReader(resourceReader);
        repository.setConverter(new ObjectTypeConverter());
        repository.setImageInformation(new ImageInformation());
        repository.setPatternsLocation(new HashMap<PatternType, String>() {{
            put(COMMONS, "images/colors");
            put(FLAGS, "images/flags");
            put(PLAINS, "images/plains");
        }});

        this.imageGenerator = new BasicImageGenerator();
        this.canonicalImage         = ImageIO.read(resourceReader.readFile("images/canonical.jpg"));
        this.patterns               = repository.getFlags();
        this.expectedColumnsNumber  = 200;

        this.settings = new ImageGeneratorSettings() {{
            setImage(canonicalImage);
            setPatterns(patterns);
            setExpectedColumnsNumber(expectedColumnsNumber);
        }};

        this.imageGenerator.setSettings(settings);
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
        settings.setExpectedColumnsNumber(100);

        List<List<BufferedImage>> matrix = imageGenerator.asMatrix();

        int matrixWidth = matrix.size();
        int matrixHeight = matrix.get(0).size();
        assertTrue(matrixWidth <= canonicalImage.getWidth()); // matrix width CAN NOT be more then image width.
        assertTrue(matrixHeight <= canonicalImage.getHeight()); // matrix height CAN NOT be more then image height.
    }

    @Test
    public void asMatrix_exception() throws Exception {
        settings.setExpectedColumnsNumber(2000);

        thrown.expect(MatrixSizeException.class);
        thrown.expectMessage("Number of expected columns (is 2000) could not be more than image width (is 1600).");

        imageGenerator.asMatrix();
    }

    @Test
    public void averagedColorsMatrix() throws Exception {
        int white = 255;
        settings.setImage(whiteImage);
        settings.setExpectedColumnsNumber(10);
        imageGenerator.averagedColorsMatrix()
                .forEach(row -> row
                        .forEach(averageRGB -> {
                            assertEquals(white, averageRGB.getRed());
                            assertEquals(white, averageRGB.getGreen());
                            assertEquals(white, averageRGB.getBlue());
                        }));
    }

    @Test
    public void resultMatrix() throws Exception {
        imageGenerator.resultMatrix()
                .forEach(row -> row
                        .forEach(patternImg -> {
                            assertTrue(patterns.values().contains(patternImg));
                        }));
    }

    @Test
    public void generateImage() throws Exception {
        BufferedImage generatedImage = imageGenerator.generateImage();

        assertNotEquals(whiteImage, generatedImage);
        assertTrue(generatedImage.getWidth() >= whiteImage.getWidth());
        assertTrue(generatedImage.getHeight() >= whiteImage.getHeight());
    }
}