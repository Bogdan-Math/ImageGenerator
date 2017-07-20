package layers.repository;

import domain.PatternType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import utility.helpers.PatternManager;
import utility.helpers.ResourceReader;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

import static domain.PatternType.*;

@Repository
@Scope("singleton")
public class BasicPatternsRepository implements PatternsRepository {

    @Autowired
    private PatternManager patternManager;

    @Autowired
    private ResourceReader resourceReader;

    @Resource(name = "patternsLocation")
    private Map<PatternType, String> patternsLocation;

    @Override
    public Map<Color, BufferedImage> getCommons() {
        return initialize(COMMONS);
    }

    @Override
    public Map<Color, BufferedImage> getFlags() {
        return initialize(FLAGS);
    }

    @Override
    public Map<Color, BufferedImage> getPlains() {
        return initialize(PLAINS);
    }

    private Map<Color, BufferedImage> initialize(PatternType patternType) {
        return patternManager.patternsMap(resourceReader.readFiles(patternsLocation.get(patternType)));
    }
}