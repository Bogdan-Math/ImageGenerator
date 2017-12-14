package layer.service;

import domain.InformationalColor;
import domain.InformationalImage;
import domain.PatternType;

import java.util.Map;

public interface PatternImageService {

    void cacheAllPatterns();

    //TODO: rename this method
    Map<PatternType, Map<InformationalColor, InformationalImage>> getAllPatterns();
}
