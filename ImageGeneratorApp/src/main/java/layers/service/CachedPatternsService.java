package layers.service;

import core.InformationalImage;
import core.PatternType;

import java.awt.*;
import java.util.Map;

public interface CachedPatternsService {

    void cacheAllPatterns();
    Map<PatternType, Map<Color, InformationalImage>> getAllPatterns();
}
