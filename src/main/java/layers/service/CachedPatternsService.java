package layers.service;

import domain.PatternType;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public interface CachedPatternsService {

    void cacheAllPatterns();
    Map<PatternType, List<BufferedImage>> getAllPatterns();
}
