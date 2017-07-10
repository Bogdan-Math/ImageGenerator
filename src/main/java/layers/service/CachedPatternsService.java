package layers.service;

import layers.repository.PatternsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;

import static layers.service.PatternsType.*;

@Service
@Scope("singleton")
public class CachedPatternsService implements PatternsService {

    @Autowired
    private PatternsRepository repository;

    private Map<PatternsType, Map<Color, BufferedImage>> patterns;

    @PostConstruct
    public void postConstruct() {
        Map<Color, BufferedImage> commons = repository.getCommons();
        Map<Color, BufferedImage> flags   = repository.getFlags();
        Map<Color, BufferedImage> plains  = repository.getPlains();

        patterns = new LinkedHashMap<>();

        patterns.put(COMMONS, commons);
        patterns.put(FLAGS, flags);
        patterns.put(PLAINS, plains);
    }

    @Override
    public Map<PatternsType, Map<Color, BufferedImage>> getAllPatterns() {
        return patterns;
    }
}