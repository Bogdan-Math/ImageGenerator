package layers.service;

import domain.PatternType;
import layers.repository.PatternsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CachedPatternsServiceImplTest {

    @InjectMocks
    private CachedPatternsServiceImpl patternsService;

    @Mock
    private PatternsRepository repository;

    @Before
    public void setUp() throws Exception {

        when(repository.getCommons()).thenReturn(new ArrayList<BufferedImage>(){{
            add(new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB));
        }});

        when(repository.getFlags()).thenReturn(new ArrayList<BufferedImage>(){{
            add(new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB));
        }});

        when(repository.getPlains()).thenReturn(new ArrayList<BufferedImage>(){{
            add(new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB));
        }});

    }

    @Test
    public void cacheAllPatternsAndGetAllPatterns() throws Exception {
        patternsService.cacheAllPatterns();
        Map<PatternType, List<BufferedImage>> allPatterns = patternsService.getAllPatterns();

        assertNotNull(allPatterns);
        assertThat(allPatterns.size(), is(3));
        assertThat(allPatterns.get(PatternType.COMMONS).size(), is(1));
        assertThat(allPatterns.get(PatternType.FLAGS).size(),   is(1));
        assertThat(allPatterns.get(PatternType.PLAINS).size(),  is(1));
    }
}