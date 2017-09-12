package domain;

import layers.repository.PatternsRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import utility.helper.ImageInformation;
import utility.helper.ObjectTypeConverter;
import utility.helper.ResourceReader;
import utility.pattern.PatternType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static utility.pattern.PatternType.*;

//TODO: add more code coverage
public class BasicImageGeneratorTest {

    private ImageGenerator imageGenerator;

    private Settings settings;

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
        this.canonicalImage         = ImageIO.read(resourceReader.readFile("images/music_man.jpg"));
        this.patterns               = repository.getFlags();
        this.expectedColumnsNumber  = 100;

        this.settings = new BasicSettings() {{
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
    public void generateImage() throws Exception {
        BufferedImage generatedImage = imageGenerator.generateImage();

        assertNotEquals(whiteImage, generatedImage);
        assertTrue(generatedImage.getWidth() >= whiteImage.getWidth());
        assertTrue(generatedImage.getHeight() >= whiteImage.getHeight());
    }
}