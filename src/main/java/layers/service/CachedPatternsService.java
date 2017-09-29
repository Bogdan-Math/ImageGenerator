package layers.service;

import utility.pattern.InformationalImage;
import utility.pattern.PatternType;

import java.awt.*;
import java.util.Map;

public interface CachedPatternsService {

    void cacheAllPatterns();
    Map<PatternType, Map<Color, InformationalImage>> getAllPatterns();
}
