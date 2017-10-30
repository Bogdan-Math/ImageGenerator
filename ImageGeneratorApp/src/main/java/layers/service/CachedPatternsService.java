package layers.service;

import domain.InformationalColor;
import domain.InformationalImage;
import domain.PatternType;

import java.util.Map;

public interface CachedPatternsService {

    void cacheAllPatterns();
    Map<PatternType, Map<InformationalColor, InformationalImage>> getAllPatterns();
}
