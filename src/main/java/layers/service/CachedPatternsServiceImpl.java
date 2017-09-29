package layers.service;

import layers.repository.PatternsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import utility.pattern.InformationalImage;
import utility.pattern.PatternType;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static utility.pattern.PatternType.*;

@Service
@Scope("singleton")
public class CachedPatternsServiceImpl implements CachedPatternsService {

    @Autowired
    private PatternsRepository repository;

    private Map<PatternType, Map<Color, InformationalImage>> allPatterns;

    @Override
    @PostConstruct
    public void cacheAllPatterns() {
        Map<Color, InformationalImage> commons = repository.getCommons();
        Map<Color, InformationalImage> flags   = repository.getFlags();
        Map<Color, InformationalImage> plains  = repository.getPlains();

        allPatterns = new LinkedHashMap<>();

        allPatterns.put(COMMONS, commons);
        allPatterns.put(FLAGS, flags);
        allPatterns.put(PLAINS, plains);
    }

    @Override
    public Map<PatternType, Map<Color, InformationalImage>> getAllPatterns() {
        return allPatterns;
    }
}