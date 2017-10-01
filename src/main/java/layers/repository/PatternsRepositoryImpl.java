package layers.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import utility.helper.ObjectTypeConverter;
import utility.helper.ResourceReader;
import utility.pattern.InformationalImage;
import utility.pattern.PatternType;

import javax.annotation.Resource;
import java.awt.*;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static utility.pattern.PatternType.*;

@Repository
@Scope("singleton")
public class PatternsRepositoryImpl implements PatternsRepository {

    @Autowired
    private ResourceReader resourceReader;

    @Autowired
    private ObjectTypeConverter converter;

    @Resource(name = "patternsLocation")
    private Map<PatternType, String> patternsLocation;

    @Override
    public Map<Color, InformationalImage> getCommons() {
        return initialize(COMMONS);
    }

    @Override
    public Map<Color, InformationalImage> getFlags() {
        return initialize(FLAGS);
    }

    @Override
    public Map<Color, InformationalImage> getPlains() {
        return initialize(PLAINS);
    }

    private Map<Color, InformationalImage> initialize(PatternType patternType) {
        return resourceReader.readFiles(patternsLocation.get(patternType))
                             .stream()
                             .collect(toMap(file -> converter.informationalImage(file).averagedColor(),// put Color              as KEY   in map
                                            file -> converter.informationalImage(file),                // put InformationalImage as VALUE in map
                                            (img_color_1, img_color_2) -> {
                                                System.out.println("Two same average color: ");
                                                System.out.println(img_color_1);
                                                System.out.println(img_color_2);

                                                return img_color_1;
                                            }
                                     )
                             );
    }

    //TODO: delete this method, after add Spring to tests
    public void setResourceReader(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
    }

    //TODO: delete this method, after add Spring to tests
    public void setConverter(ObjectTypeConverter converter) {
        this.converter = converter;
    }

    //TODO: delete this method, after add Spring to tests
    public void setPatternsLocation(Map<PatternType, String> patternsLocation) {
        this.patternsLocation = patternsLocation;
    }
}