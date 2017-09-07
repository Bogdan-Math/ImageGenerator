package layers.repository;

import domain.PatternType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import utility.helpers.ObjectTypeConverter;
import utility.helpers.ResourceReader;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static domain.PatternType.*;

@Repository
@Scope("singleton")
public class BasicPatternsRepository implements PatternsRepository {

    @Autowired
    private ResourceReader resourceReader;

    @Autowired
    private ObjectTypeConverter converter;

    @Resource(name = "patternsLocation")
    private Map<PatternType, String> patternsLocation;

    @Override
    public List<BufferedImage> getCommons() {
        return initialize(COMMONS);
    }

    @Override
    public List<BufferedImage> getFlags() {
        return initialize(FLAGS);
    }

    @Override
    public List<BufferedImage> getPlains() {
        return initialize(PLAINS);
    }

    private List<BufferedImage> initialize(PatternType patternType) {
        return resourceReader.readFiles(patternsLocation.get(patternType))
                             .stream()
                             .map(file -> converter.bufferedImage(file))
                             .collect(Collectors.toList());
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