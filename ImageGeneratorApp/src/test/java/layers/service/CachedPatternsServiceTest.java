package layers.service;

import domain.InformationalColor;
import domain.InformationalImage;
import domain.PatternType;
import layers.repository.PatternsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static domain.InformationalColor.WHITE;
import static domain.InformationalImage.TYPE_INT_RGB;
import static domain.PatternType.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class CachedPatternsServiceTest {

    @Autowired
    private CachedPatternsService patternsService;

    @Autowired
    private PatternsRepository repository;

    @Before
    public void setUp() throws Exception {

        when(repository.getCommons()).thenReturn(new HashMap<InformationalColor, InformationalImage>() {{
            put(WHITE, new InformationalImage(1, 1, TYPE_INT_RGB));
        }});

        when(repository.getFlags()).thenReturn(new HashMap<InformationalColor, InformationalImage>() {{
            put(WHITE, new InformationalImage(1, 1, TYPE_INT_RGB));
        }});

        when(repository.getPlains()).thenReturn(new HashMap<InformationalColor, InformationalImage>() {{
            put(WHITE, new InformationalImage(1, 1, TYPE_INT_RGB));
        }});

    }

    @Test
    public void cacheAllPatternsAndGetAllPatterns() throws Exception {
        patternsService.cacheAllPatterns();
        Map<PatternType, Map<InformationalColor, InformationalImage>> allPatterns = patternsService.getAllPatterns();

        assertNotNull(allPatterns);
        assertThat(allPatterns.size(), is(3));
        assertThat(allPatterns.get(COMMONS).size(), is(1));
        assertThat(allPatterns.get(FLAGS).size(),   is(1));
        assertThat(allPatterns.get(PLAINS).size(),  is(1));
    }
}