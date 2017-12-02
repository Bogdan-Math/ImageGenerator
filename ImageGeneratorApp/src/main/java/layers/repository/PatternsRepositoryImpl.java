package layers.repository;

import domain.InformationalColor;
import domain.InformationalImage;
import domain.PatternType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import system.ResourceReader;

import javax.annotation.Resource;
import java.util.Map;

import static domain.PatternType.*;
import static java.util.stream.Collectors.toMap;

@Scope("singleton")
@Repository("patternsRepository")
public class PatternsRepositoryImpl implements PatternsRepository {

    private ResourceReader resourceReader;

    @Override
    public Map<InformationalColor, InformationalImage> getCommons() {
        return initialize(COMMONS);
    }

    @Override
    public Map<InformationalColor, InformationalImage> getFlags() {
        return initialize(FLAGS);
    }

    @Override
    public Map<InformationalColor, InformationalImage> getPlains() {
        return initialize(PLAINS);
    }

    private Map<InformationalColor, InformationalImage> initialize(PatternType patternType) {
        return resourceReader.readFiles(patternType.getLocation())
                             .stream()
                             .map(InformationalImage::from)
                             .collect(toMap(InformationalImage::averagedColor,       // put Color              as KEY   in map
                                            informationalImage -> informationalImage,// put InformationalImage as VALUE in map
                                            (img_color_1, img_color_2) -> {
                                                System.out.println("Two same averaged colors: ");
                                                System.out.println(img_color_1);
                                                System.out.println(img_color_2);

                                                return img_color_1;
                                            }
                                     )
                             );
    }

    @Resource(name = "resourceReader")
    public void setResourceReader(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
    }
}