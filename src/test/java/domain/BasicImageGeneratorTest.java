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
import utility.system.ObjectTypeConverter;
import utility.system.ResourceReader;
import utility.core.InformationalImage;

import java.awt.*;
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

    private InformationalImage originalImage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Map<Color, InformationalImage> patterns = resourceReader.readFiles("images/colors").stream().collect(toMap(
                file -> typeConverter.informationalImage(file).averagedColor(),
                file -> typeConverter.informationalImage(file)
        ));

        when(settings.getPatterns()).thenReturn(patterns);
        when(settings.getExpectedColumnsNumber()).thenReturn(31); //31 -  because we want to move into incrementing loop for image width
        when(settings.getSubImage(anyInt(), anyInt(), anyInt(), anyInt())).thenCallRealMethod();
    }

    @Test
    public void generateWhiteImage() throws Exception {

        //Arrange
        this.originalImage = typeConverter.informationalImage(resourceReader.readFile("images/testable/1-white.jpg"));
        when(settings.getIncomeImage()).thenReturn(originalImage);
        when(settings.getImageWidth()).thenReturn(originalImage.getWidth());
        when(settings.getImageHeight()).thenReturn(originalImage.getHeight());

        //Act
        InformationalImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25
        assertThat(generatedImage.averagedColor(), is(WHITE));
    }

    @Test
    public void generateGrayImage() throws Exception {

        //Arrange
        this.originalImage = typeConverter.informationalImage(resourceReader.readFile("images/testable/2-gray.jpg"));
        when(settings.getIncomeImage()).thenReturn(originalImage);
        when(settings.getImageWidth()).thenReturn(originalImage.getWidth());
        when(settings.getImageHeight()).thenReturn(originalImage.getHeight());

        //Act
        InformationalImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25
        assertThat(generatedImage.averagedColor(), is(GRAY));
    }

    @Test
    public void generateBlackImage() throws Exception {

        //Arrange
        this.originalImage = typeConverter.informationalImage(resourceReader.readFile("images/testable/3-black.jpg"));
        when(settings.getIncomeImage()).thenReturn(originalImage);
        when(settings.getImageWidth()).thenReturn(originalImage.getWidth());
        when(settings.getImageHeight()).thenReturn(originalImage.getHeight());

        //Act
        InformationalImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25
        assertThat(generatedImage.averagedColor(), is(BLACK));
    }

    @Test
    public void generate4x4Image() throws Exception {

        //Arrange
        this.originalImage = typeConverter.informationalImage(resourceReader.readFile("images/testable/4x4.jpg"));
        when(settings.getIncomeImage()).thenReturn(originalImage);
        when(settings.getImageWidth()).thenReturn(originalImage.getWidth());
        when(settings.getImageHeight()).thenReturn(originalImage.getHeight());

        //Act
        InformationalImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25

        assertThat(almostIdentical(generatedImage.getSubImage(0, 0, 200, 200).averagedColor(), BLACK), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(200, 0, 200, 200).averagedColor(), WHITE), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(0, 200, 200, 200).averagedColor(), WHITE), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(200, 200, 200, 200).averagedColor(), BLUE), is(true));

        assertThat(almostIdentical(generatedImage.getSubImage(400, 0, 200, 200).averagedColor(), GREEN), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(600, 0, 200, 200).averagedColor(), WHITE), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(400, 200, 200, 200).averagedColor(), WHITE), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(600, 200, 200, 200).averagedColor(), RED), is(true));

        assertThat(almostIdentical(generatedImage.getSubImage(0, 400, 200, 200).averagedColor(), RED), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(200, 400, 200, 200).averagedColor(), WHITE), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(0, 600, 200, 200).averagedColor(), WHITE), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(200, 600, 200, 200).averagedColor(), GREEN), is(true));

        assertThat(almostIdentical(generatedImage.getSubImage(400, 400, 200, 200).averagedColor(), BLUE), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(600, 400, 200, 200).averagedColor(), WHITE), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(400, 600, 200, 200).averagedColor(), WHITE), is(true));
        assertThat(almostIdentical(generatedImage.getSubImage(600, 600, 200, 200).averagedColor(), BLACK), is(true));

        assertThat(almostIdentical(generatedImage.getSubImage(600, 600, 200, 200).averagedColor(), WHITE), is(false));
    }

    private boolean almostIdentical(Color colorOne, Color colorTwo) {
        return almostIdentical(colorOne.getRed(), colorTwo.getRed()) &&
               almostIdentical(colorOne.getGreen(), colorTwo.getGreen()) &&
               almostIdentical(colorOne.getBlue(), colorTwo.getBlue());
    }

    private boolean almostIdentical(int firstColor, int secondColor) {
        BigDecimal delta        = valueOf(255.0).divide(valueOf(3.0), MathContext.DECIMAL32);
        BigDecimal firstValue   = valueOf(firstColor); // have to add 0.01 to avoid division by zero
        BigDecimal secondValue  = valueOf(secondColor);// have to add 0.01 to avoid division by zero
        BigDecimal smallerValue = (firstValue.compareTo(secondValue) < 0) ? firstValue : secondValue;
        BigDecimal greaterValue = (firstValue.compareTo(secondValue) < 0) ? secondValue : firstValue;

        return smallerValue.add(delta, MathContext.DECIMAL32).compareTo(greaterValue) > 0;
    }

}