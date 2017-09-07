package layers.service;

import domain.PatternType;
import layers.repository.PatternsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static domain.PatternType.*;

@Service
@Scope("singleton")
public class CachedPatternsServiceImpl implements CachedPatternsService {

    @Autowired
    private PatternsRepository repository;

    private Map<PatternType, List<BufferedImage>> allPatterns;

    @Override
    @PostConstruct
    public void cacheAllPatterns() {
        List<BufferedImage> commons = repository.getCommons();
        List<BufferedImage> flags   = repository.getFlags();
        List<BufferedImage> plains  = repository.getPlains();

        allPatterns = new LinkedHashMap<>();

        allPatterns.put(COMMONS, commons);
        allPatterns.put(FLAGS,   flags);
        allPatterns.put(PLAINS,  plains);
    }

    @Override
    public Map<PatternType, List<BufferedImage>> getAllPatterns() {
        return allPatterns;
    }
}