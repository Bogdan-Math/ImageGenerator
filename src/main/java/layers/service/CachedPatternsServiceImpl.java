package layers.service;

import domain.PatternType;
import layers.repository.PatternsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

import static domain.PatternType.*;

@Service
@Scope("singleton")
public class CachedPatternsServiceImpl implements CachedPatternsService {

    @Autowired
    private PatternsRepository repository;

    private Map<PatternType, Map<Color, BufferedImage>> allPatterns;

    @Override
    @PostConstruct
    public void cacheAllPatterns() {
        Map<Color, BufferedImage> commons = repository.getCommons();
        Map<Color, BufferedImage> flags   = repository.getFlags();
        Map<Color, BufferedImage> plains  = repository.getPlains();

        allPatterns = new LinkedHashMap<>();

        allPatterns.put(COMMONS, commons);
        allPatterns.put(FLAGS, flags);
        allPatterns.put(PLAINS, plains);
    }

    @Override
    public Map<PatternType, Map<Color, BufferedImage>> getAllPatterns() {
        return allPatterns;
    }
}