package layers.service;

import domain.InformationalColor;
import domain.InformationalImage;
import domain.PatternType;
import layers.repository.PatternImageRepository;
import model.PatternImage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static domain.PatternType.*;
import static java.util.stream.Collectors.toMap;

@Service
@Scope("singleton")
public class CachedPatternsServiceImpl implements CachedPatternsService {

    private PatternImageRepository repository;

    private Map<PatternType, Map<InformationalColor, InformationalImage>> allPatterns;

    @Override
    @PostConstruct
    public void cacheAllPatterns() {
        List<PatternImage> commons = repository.getCommons();
        List<PatternImage> flags   = repository.getFlags();
        List<PatternImage> plains  = repository.getPlains();

        allPatterns = new LinkedHashMap<>();

        allPatterns.put(COMMONS, convert(commons));
        allPatterns.put(FLAGS,   convert(flags));
        allPatterns.put(PLAINS,  convert(plains));
    }

    private Map<InformationalColor, InformationalImage> convert(List<PatternImage> patterns) {
        return patterns.stream()
                .map(patternImage -> InformationalImage.from(patternImage.getBytes()))
                .collect(toMap(
                        InformationalImage::averagedColor,       // put InformationalColor as KEY   in map
                        informationalImage -> informationalImage,// put InformationalImage as VALUE in map
                        (img_color_1, img_color_2) -> {
                            System.out.println("Two same averaged colors: ");
                            System.out.println(img_color_1);
                            System.out.println(img_color_2);

                            return img_color_1;
                        }
                ));
    }

    @Override
    public Map<PatternType, Map<InformationalColor, InformationalImage>> getAllPatterns() {
        return allPatterns;
    }

    @Resource(name = "patternsRepository")
    public void setRepository(PatternImageRepository repository) {
        this.repository = repository;
    }
}