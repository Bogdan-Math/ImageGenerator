package layers.repository;

import layers.service.PatternsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import utility.helpers.PatternManager;
import utility.helpers.ResourceReader;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

import static layers.service.PatternsType.*;

@Repository
@Scope("singleton")
public class BasicPatternsRepository implements PatternsRepository {

    @Autowired
    private PatternManager patternManager;

    @Autowired
    private ResourceReader resourceReader;

    @Resource(name = "patternsLocation")
    private Map<PatternsType, String> patternsLocation;

    @Override
    public Map<Color, BufferedImage> getCommons() {
        return patternsInitialization(COMMONS);
    }

    @Override
    public Map<Color, BufferedImage> getFlags() {
        return patternsInitialization(FLAGS);
    }

    @Override
    public Map<Color, BufferedImage> getPlains() {
        return patternsInitialization(PLAINS);
    }

    private Map<Color, BufferedImage> patternsInitialization(PatternsType patternsType) {
        return patternManager.patternsMap(resourceReader.readFiles(patternsLocation.get(patternsType)));
    }
}