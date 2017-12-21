package layer.service;

import domain.InformationalColor;
import domain.InformationalImage;
import model.PatternType;
import layer.repository.PatternRepository;
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
public class PatternServiceTest {

    @Autowired
    private PatternService patternService;

    @Autowired
    private PatternRepository patternRepository;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));

        final byte[] imageByteArray = new ResourceReader().readAllIn("images/plains/").asByteArrays()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Oops, something WRONG happens in your test!"));

        when(patternRepository.getCommons()).thenReturn(Stream.of(
                new InformationalImage(imageByteArray),
                new InformationalImage(imageByteArray),
                new InformationalImage(imageByteArray)));

        when(patternRepository.getFlags()).thenReturn(Stream.of(
                new InformationalImage(imageByteArray),
                new InformationalImage(imageByteArray)));

        when(patternRepository.getPlains()).thenReturn(Stream.of(
                new InformationalImage(imageByteArray)));
    }

    @Test
    public void cacheAndGetAllPatterns() throws Exception {
        patternService.cacheAllPatterns();
        Map<PatternType, Map<InformationalColor, InformationalImage>> allPatterns = patternService.getAllPatterns();

        assertThat(allPatterns.size(), is(3));
        assertThat(allPatterns.get(COMMONS).size(), is(1));
        assertThat(allPatterns.get(FLAGS).size(),   is(1));
        assertThat(allPatterns.get(PLAINS).size(),  is(1));
    }

    @Test
    public void twoSameAveragedColors() throws Exception {
        patternService.cacheAllPatterns();
        Map<PatternType, Map<InformationalColor, InformationalImage>> allPatterns = patternService.getAllPatterns();

        assertNotNull(allPatterns);
        assertThat(outContent.toString(), containsString("Two same averaged colors:"));
    }

}