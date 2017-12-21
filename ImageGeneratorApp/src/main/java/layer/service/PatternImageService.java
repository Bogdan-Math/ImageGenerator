package layer.service;

import domain.InformationalColor;
import domain.InformationalImage;
import model.PatternType;

import java.util.Map;

public interface PatternImageService {

    void cacheAllPatterns();
    Map<PatternType, Map<InformationalColor, InformationalImage>> getAllPatterns();
}
