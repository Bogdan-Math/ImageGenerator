package layers.repository;

import core.InformationalImage;
import core.PatternType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import system.ObjectTypeConverter;
import system.ResourceReader;

import java.awt.*;
import java.util.Map;

import static core.PatternType.*;
import static java.util.stream.Collectors.toMap;

@Repository
@Scope("singleton")
public class PatternsRepositoryImpl implements PatternsRepository {

    @Autowired
    private ResourceReader resourceReader;

    @Autowired
    private ObjectTypeConverter converter;

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
        return resourceReader.readFiles(patternType.getLocation())
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
}