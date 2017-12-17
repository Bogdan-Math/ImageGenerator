package layer.repository;

import domain.PatternType;
import model.PatternImage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import system.ResourceReader;

import javax.annotation.Resource;
import java.util.stream.Stream;

import static domain.PatternType.*;

@Scope("singleton")
@Repository("patternImageRepository")
public class PatternImageRepositoryImpl implements PatternImageRepository {

    private ResourceReader resourceReader;

    @Override
    public Stream<PatternImage> getCommons() {
        return initialize(COMMONS);
    }

    @Override
    public Stream<PatternImage> getFlags() {
        return initialize(FLAGS);
    }

    @Override
    public Stream<PatternImage> getPlains() {
        return initialize(PLAINS);
    }

    private Stream<PatternImage> initialize(PatternType patternType) {
        return resourceReader.readAll(patternType.getLocation())
                .asByteArrays()
                .map(PatternImage::new);
    }

    @Resource(name = "resourceReader")
    public void setResourceReader(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
    }
}