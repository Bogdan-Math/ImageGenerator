package layer.service;

import domain.InformationalColor;
import domain.InformationalImage;
import layer.repository.PatternImageRepository;
import model.PatternType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import system.ResourceReader;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.stream.Stream;

import static model.PatternType.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class PatternImageServiceTest {

    @Autowired
    private PatternImageService patternImageService;

    @Autowired
    private PatternImageRepository patternImageRepository;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));

        final byte[] imageByteArray = new ResourceReader().readAllIn("images/plains/").asByteArrays()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Oops, something WRONG happens in your test!"));

        when(patternImageRepository.getCommons()).thenReturn(Stream.of(
                new InformationalImage(imageByteArray),
                new InformationalImage(imageByteArray),
                new InformationalImage(imageByteArray)));

        when(patternImageRepository.getFlags()).thenReturn(Stream.of(
                new InformationalImage(imageByteArray),
                new InformationalImage(imageByteArray)));

        when(patternImageRepository.getPlains()).thenReturn(Stream.of(
                new InformationalImage(imageByteArray)));
    }

    @Test
    public void cacheAndGetAllPatterns() {
        patternImageService.cacheAllPatterns();
        Map<PatternType, Map<InformationalColor, InformationalImage>> allPatterns = patternImageService.getAllPatterns();

        assertThat(allPatterns.size(), is(3));
        assertThat(allPatterns.get(COMMONS).size(), is(1));
        assertThat(allPatterns.get(FLAGS).size(),   is(1));
        assertThat(allPatterns.get(PLAINS).size(),  is(1));
    }

    @Test
    public void twoSameAveragedColors() {
        patternImageService.cacheAllPatterns();
        Map<PatternType, Map<InformationalColor, InformationalImage>> allPatterns = patternImageService.getAllPatterns();

        assertNotNull(allPatterns);
        assertThat(outContent.toString(), containsString("Two same averaged colors:"));
    }

}