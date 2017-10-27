package layers.service;

import utility.core.InformationalImage;
import utility.core.PatternType;

import java.awt.*;
import java.util.Map;

public interface CachedPatternsService {

    void cacheAllPatterns();
    Map<PatternType, Map<Color, InformationalImage>> getAllPatterns();
}
