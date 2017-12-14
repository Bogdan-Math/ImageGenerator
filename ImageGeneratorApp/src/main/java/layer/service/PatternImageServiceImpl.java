package layer.service;

import domain.InformationalColor;
import domain.InformationalImage;
import domain.PatternType;
import layer.repository.PatternImageRepository;
import model.PatternImage;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static domain.PatternType.*;
import static java.util.stream.Collectors.toMap;

@Service("patternImageService")
public class PatternImageServiceImpl implements PatternImageService {

    private PatternImageRepository patternImageRepository;

    private Map<PatternType, Map<InformationalColor, InformationalImage>> allPatterns;

    @Override
    @PostConstruct
    public void cacheAllPatterns() {
        List<PatternImage> commons = patternImageRepository.getCommons();
        List<PatternImage> flags   = patternImageRepository.getFlags();
        List<PatternImage> plains  = patternImageRepository.getPlains();

        allPatterns = new LinkedHashMap<>();

        allPatterns.put(COMMONS, convert(commons));
        allPatterns.put(FLAGS,   convert(flags));
        allPatterns.put(PLAINS,  convert(plains));
    }

    private Map<InformationalColor, InformationalImage> convert(List<PatternImage> patterns) {
        return patterns.stream()
                .map(patternImage -> InformationalImage.madeOf(patternImage.fullImage))
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
    public Map<PatternType, Map<InformationalColor, InformationalImage>> getInformationalMaps() {
        return allPatterns;
    }

    @Resource(name = "patternImageRepository")
    public void setPatternImageRepository(PatternImageRepository patternImageRepository) {
        this.patternImageRepository = patternImageRepository;
    }
}