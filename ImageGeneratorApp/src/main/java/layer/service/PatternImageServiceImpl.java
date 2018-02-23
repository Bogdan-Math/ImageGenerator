package layer.service;

import domain.InformationalColor;
import domain.InformationalImage;
import layer.repository.PatternImageRepository;
import model.PatternType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static model.PatternType.*;
import static java.util.stream.Collectors.toMap;

@Service("patternImageService")
public class PatternImageServiceImpl implements PatternImageService {

    private PatternImageRepository patternImageRepository;

    private Map<PatternType, Map<InformationalColor, InformationalImage>> allPatterns;

    @Override
    @PostConstruct
    public void cacheAllPatterns() {
        Stream<InformationalImage> commons = patternImageRepository.getCommons();
        Stream<InformationalImage> flags   = patternImageRepository.getFlags();
        Stream<InformationalImage> plains  = patternImageRepository.getPlains();

        allPatterns = new LinkedHashMap<>();

        allPatterns.put(COMMONS, convert(commons));
        allPatterns.put(FLAGS, convert(flags));
        allPatterns.put(PLAINS, convert(plains));
    }

    private Map<InformationalColor, InformationalImage> convert(Stream<InformationalImage> patterns) {
        return patterns.collect(toMap(
                InformationalImage::averagedColor,       // put InformationalColor as KEY   in map
                informationalImage -> informationalImage,// put InformationalImage as VALUE in map
                (img_color_1, img_color_2) -> {
                    System.out.println("Two same averaged colors: ");//TODO: add logs
                    System.out.println(img_color_1);
                    System.out.println(img_color_2);

                    return img_color_1;
                }));
    }

    @Override
    public Map<PatternType, Map<InformationalColor, InformationalImage>> getAllPatterns() {
        return allPatterns;
    }

    @Resource(name = "patternImageRepository")
    public void setPatternImageRepository(PatternImageRepository patternImageRepository) {
        this.patternImageRepository = patternImageRepository;
    }
}