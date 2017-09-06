package layers.service;

import utility.config.ImageGenerationConfig;
import domain.PatternType;
import layers.repository.BasicPatternsRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import utility.exceptions.MatrixSizeException;
import utility.helpers.ImageInformation;
import utility.helpers.ObjectTypeConverter;
import utility.helpers.ResourceReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

import static domain.PatternType.*;
import static org.junit.Assert.*;

public class ImageGenerationServiceImplTest {

    private ResourceReader resourceReader;

    private BasicPatternsRepository repository;

    private ImageGenerationService imageGenerationService;

    private BufferedImage whiteImage;
    private BufferedImage canonicalImage;

    private Map<Color, BufferedImage> patterns;
    private Integer expectedColumnsNumber;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private ImageGenerationConfig config;

    @Before
    public void setUp() throws Exception {
        this.resourceReader = new ResourceReader();

        this.repository = new BasicPatternsRepository();
        this.repository.setResourceReader(new ResourceReader());
        this.repository.setConverter(new ObjectTypeConverter());
        this.repository.setImageInformation(new ImageInformation());
        this.repository.setPatternsLocation(new HashMap<PatternType, String>() {{
            put(COMMONS, "images/colors");
            put(FLAGS, "images/flags");
            put(PLAINS, "images/plains");
        }});

        this.canonicalImage = ImageIO.read(resourceReader.readFile("images/canonical.jpg"));
        this.whiteImage     = ImageIO.read(resourceReader.readFile("images/colors/1-white.jpg"));

        this.expectedColumnsNumber = 200;
        this.patterns              = repository.getFlags();// patternsMap(resourceReader.readFiles("images/flags"));

        this.imageGenerationService = new ImageGenerationServiceImpl();
        config = new ImageGenerationConfig() {{
            setImage(canonicalImage);
            setPatterns(patterns);
            setExpectedColumnsNumber(expectedColumnsNumber);
        }};
        this.imageGenerationService.setConfig(config);
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

        config.setImage(canonicalImage);

        List<List<BufferedImage>> matrix = imageGenerationService.asMatrix(expectedColumn);
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

        config.setImage(canonicalImage);
        imageGenerationService.asMatrix(wrongExpectedColumns);
    }

    @Test
    public void averagedColorsMatrix() throws Exception {
        int white = 255;
        config.setImage(whiteImage);
        config.setExpectedColumnsNumber(10);
        imageGenerationService.averagedColorsMatrix()
                .forEach(row -> row
                        .forEach(averageRGB -> {
                            assertEquals(white, averageRGB.getRed());
                            assertEquals(white, averageRGB.getGreen());
                            assertEquals(white, averageRGB.getBlue());
                        }));
    }

    @Test
    public void resultMatrix() throws Exception {
        config.setExpectedColumnsNumber(250);
        imageGenerationService.resultMatrix()
                .forEach(row -> row
                        .forEach(patternImg -> {
                            assertTrue(patterns.values().contains(patternImg));
                        }));
    }

    @Test
    public void generateImage() throws Exception {
        config.setImage(whiteImage);
        config.setExpectedColumnsNumber(10);
        BufferedImage generatedImage = imageGenerationService.generateImage();

        assertNotEquals(whiteImage, generatedImage);
        assertTrue(generatedImage.getWidth() >= whiteImage.getWidth());
        assertTrue(generatedImage.getHeight() >= whiteImage.getHeight());
    }
}