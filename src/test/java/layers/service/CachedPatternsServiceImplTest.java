package layers.service;

import layers.repository.PatternsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import utility.pattern.InformationalImage;
import utility.pattern.PatternType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static java.awt.Color.WHITE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static utility.pattern.InformationalImage.TYPE_INT_RGB;
import static utility.pattern.PatternType.*;

@RunWith(MockitoJUnitRunner.class)
public class CachedPatternsServiceImplTest {

    @InjectMocks
    private CachedPatternsServiceImpl patternsService;

    @Mock
    private PatternsRepository repository;

    @Before
    public void setUp() throws Exception {

        when(repository.getCommons()).thenReturn(new HashMap<Color, InformationalImage>() {{
            put(WHITE, new InformationalImage(1, 1, TYPE_INT_RGB));
        }});

        when(repository.getFlags()).thenReturn(new HashMap<Color, InformationalImage>() {{
            put(WHITE, new InformationalImage(1, 1, TYPE_INT_RGB));
        }});

        when(repository.getPlains()).thenReturn(new HashMap<Color, InformationalImage>() {{
            put(WHITE, new InformationalImage(1, 1, TYPE_INT_RGB));
        }});

    }

    @Test
    public void cacheAllPatternsAndGetAllPatterns() throws Exception {
        patternsService.cacheAllPatterns();
        Map<PatternType, Map<Color, InformationalImage>> allPatterns = patternsService.getAllPatterns();

        assertNotNull(allPatterns);
        assertThat(allPatterns.size(), is(3));
        assertThat(allPatterns.get(COMMONS).size(), is(1));
        assertThat(allPatterns.get(FLAGS).size(),   is(1));
        assertThat(allPatterns.get(PLAINS).size(),  is(1));
    }
}