package layer.repository;

import domain.InformationalImage;
import model.PatternType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import system.ResourceReader;

import javax.annotation.Resource;
import java.util.stream.Stream;

import static model.PatternType.*;

@Scope("singleton")
@Repository("patternImageRepository")
public class PatternImageRepositoryImpl implements PatternImageRepository {

    private ResourceReader resourceReader;

    @Override
    public Stream<InformationalImage> getCommons() {
        return initialize(COMMONS);
    }

    @Override
    public Stream<InformationalImage> getFlags() {
        return initialize(FLAGS);
    }

    @Override
    public Stream<InformationalImage> getPlains() {
        return initialize(PLAINS);
    }

    private Stream<InformationalImage> initialize(PatternType patternType) {
        return resourceReader.readAllIn(patternType.location())
                .asByteArrays()
                .map(InformationalImage::new);
    }

    @Resource(name = "resourceReader")
    public void setResourceReader(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
    }
}