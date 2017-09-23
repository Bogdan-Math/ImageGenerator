package domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utility.helper.ImageInformation;
import utility.helper.ObjectTypeConverter;
import utility.helper.ResourceReader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

import static java.awt.Color.*;
import static java.math.BigDecimal.valueOf;
import static java.util.stream.Collectors.toMap;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static utility.helper.ImageInformation.averagedColor;

@ContextConfiguration(locations = {
        "classpath:spring/basic-image-generator.xml"
})
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
public class BasicImageGeneratorTest {

    @Autowired
    private ImageGenerator imageGenerator;

    @Autowired
    private Settings settings;

    @Autowired
    private ResourceReader resourceReader;

    @Autowired
    private ObjectTypeConverter typeConverter;

    private BufferedImage originalImage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Map<Color, BufferedImage> patterns = resourceReader.readFiles("images/colors").stream().collect(toMap(
                file -> ImageInformation.averagedColor(typeConverter.bufferedImage(file)),
                file -> typeConverter.bufferedImage(file)
        ));

        when(settings.getPatterns()).thenReturn(patterns);
        when(settings.getExpectedColumnsNumber()).thenReturn(32);
        when(settings.getSubImage(anyInt(), anyInt(), anyInt(), anyInt())).thenCallRealMethod();

    }

    @Test
    public void generateWhiteImage() throws Exception {

        //Arrange
        this.originalImage = typeConverter.bufferedImage(resourceReader.readFile("images/testable/1-white.jpg"));
        when(settings.getImage()).thenReturn(originalImage);
        when(settings.getImageWidth()).thenReturn(originalImage.getWidth());
        when(settings.getImageHeight()).thenReturn(originalImage.getHeight());

        //Act
        BufferedImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25
        assertThat(averagedColor(generatedImage), is(WHITE));
    }

    @Test
    public void generateGrayImage() throws Exception {

        //Arrange
        this.originalImage = typeConverter.bufferedImage(resourceReader.readFile("images/testable/2-gray.jpg"));
        when(settings.getImage()).thenReturn(originalImage);
        when(settings.getImageWidth()).thenReturn(originalImage.getWidth());
        when(settings.getImageHeight()).thenReturn(originalImage.getHeight());

        //Act
        BufferedImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25
        assertThat(averagedColor(generatedImage), is(GRAY));
    }

    @Test
    public void generateBlackImage() throws Exception {

        //Arrange
        this.originalImage = typeConverter.bufferedImage(resourceReader.readFile("images/testable/3-black.jpg"));
        when(settings.getImage()).thenReturn(originalImage);
        when(settings.getImageWidth()).thenReturn(originalImage.getWidth());
        when(settings.getImageHeight()).thenReturn(originalImage.getHeight());

        //Act
        BufferedImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25
        assertThat(averagedColor(generatedImage), is(BLACK));
    }

    @Test
    //TODO: add more code coverage
    public void generate4x4Image() throws Exception {

        //Arrange
        this.originalImage = typeConverter.bufferedImage(resourceReader.readFile("images/testable/4x4.jpg"));
        when(settings.getImage()).thenReturn(originalImage);
        when(settings.getImageWidth()).thenReturn(originalImage.getWidth());
        when(settings.getImageHeight()).thenReturn(originalImage.getHeight());

        //Act
        BufferedImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25

        assertThat(almostIdentical(averagedColor(generatedImage.getSubimage(0, 0, 200, 200)), BLACK), is(true));
        assertThat(almostIdentical(averagedColor(generatedImage.getSubimage(200, 0, 200, 200)), WHITE), is(true));
        assertThat(almostIdentical(averagedColor(generatedImage.getSubimage(0, 200, 200, 200)), WHITE), is(true));
        assertThat(almostIdentical(averagedColor(generatedImage.getSubimage(200, 200, 200, 200)), BLUE), is(true));
/*
        assertThat(averagedColor(generatedImage.getSubimage(200, 0, 200, 200)), is(WHITE));
        assertThat(averagedColor(generatedImage.getSubimage(0, 200, 200, 200)), is(WHITE));
        assertThat(averagedColor(generatedImage.getSubimage(200, 200, 200, 200)), is(BLUE));

        assertThat(averagedColor(generatedImage.getSubimage(400, 0, 200, 200)), is(WHITE));
        assertThat(averagedColor(generatedImage.getSubimage(600, 0, 200, 200)), is(BLUE));
        assertThat(averagedColor(generatedImage.getSubimage(400, 200, 200, 200)), is(WHITE));
        assertThat(averagedColor(generatedImage.getSubimage(600, 200, 200, 200)), is(RED));
*/
    }

    private boolean almostIdentical(Color colorOne, Color colorTwo) {
        return almostIdentical(colorOne.getRed(), colorTwo.getRed()) &&
               almostIdentical(colorOne.getGreen(), colorTwo.getGreen()) &&
               almostIdentical(colorOne.getBlue(), colorTwo.getBlue());
    }

    private boolean almostIdentical(int firstColor, int secondColor) {
        BigDecimal firstValue  = valueOf(firstColor).add(valueOf(0.01), MathContext.DECIMAL32);
        BigDecimal secondValue = valueOf(secondColor).add(valueOf(0.01), MathContext.DECIMAL32);

        BigDecimal greaterRatio = (firstValue.compareTo(secondValue) > 0) ?
                                   firstValue.divide(secondValue, MathContext.DECIMAL32) :
                                   secondValue.divide(firstValue, MathContext.DECIMAL32);

        return greaterRatio.remainder(BigDecimal.ONE).compareTo(valueOf(0.05)) < 0;
    }

}