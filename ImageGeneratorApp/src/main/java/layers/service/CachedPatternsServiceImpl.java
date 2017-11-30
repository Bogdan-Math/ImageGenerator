package layers.service;

import domain.InformationalColor;
import domain.InformationalImage;
import domain.PatternType;
import layers.repository.PatternsRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

import static domain.PatternType.*;

@Service
@Scope("singleton")
public class CachedPatternsServiceImpl implements CachedPatternsService {

    private PatternsRepository repository;

    private Map<PatternType, Map<InformationalColor, InformationalImage>> allPatterns;

    @Override
    @PostConstruct
    public void cacheAllPatterns() {
        Map<InformationalColor, InformationalImage> commons = repository.getCommons();
        Map<InformationalColor, InformationalImage> flags   = repository.getFlags();
        Map<InformationalColor, InformationalImage> plains  = repository.getPlains();

        allPatterns = new LinkedHashMap<>();

        allPatterns.put(COMMONS, commons);
        allPatterns.put(FLAGS, flags);
        allPatterns.put(PLAINS, plains);
    }

    @Override
    public Map<PatternType, Map<InformationalColor, InformationalImage>> getAllPatterns() {
        return allPatterns;
    }

    @Resource(name = "patternsRepository")
    public void setRepository(PatternsRepository repository) {
        this.repository = repository;
    }
}