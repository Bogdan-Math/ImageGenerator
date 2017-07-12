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

    private Map<Color, BufferedImage> initialize(PatternsType patternsType) {
        return patternManager.patternsMap(resourceReader.readFiles(patternsLocation.get(patternsType)));
    }
}