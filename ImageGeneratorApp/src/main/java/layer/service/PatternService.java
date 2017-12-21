package layer.service;

import domain.InformationalColor;
import domain.InformationalImage;
import model.PatternType;

import java.util.Map;

public interface PatternService {

    void cacheAllPatterns();
    Map<PatternType, Map<InformationalColor, InformationalImage>> getAllPatterns();
}
