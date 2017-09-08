package layers.service;

import utility.pattern.PatternType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public interface CachedPatternsService {

    void cacheAllPatterns();
    Map<PatternType, Map<Color, BufferedImage>> getAllPatterns();
}
