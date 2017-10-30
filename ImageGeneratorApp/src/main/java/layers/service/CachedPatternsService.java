package layers.service;

import domain.InformationalImage;
import domain.PatternType;

import java.awt.*;
import java.util.Map;

public interface CachedPatternsService {

    void cacheAllPatterns();
    Map<PatternType, Map<Color, InformationalImage>> getAllPatterns();
}
