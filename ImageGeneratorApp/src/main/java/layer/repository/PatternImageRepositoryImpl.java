package layer.repository;

import domain.PatternType;
import model.PatternImage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import system.ResourceReader;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import static domain.PatternType.*;
import static java.nio.file.Files.readAllBytes;
import static java.util.stream.Collectors.toList;

@Scope("singleton")
@Repository("patternImageRepository")
public class PatternImageRepositoryImpl implements PatternImageRepository {

    private ResourceReader resourceReader;

    @Override
    public List<PatternImage> getCommons() {
        return initialize(COMMONS);
    }

    @Override
    public List<PatternImage> getFlags() {
        return initialize(FLAGS);
    }

    @Override
    public List<PatternImage> getPlains() {
        return initialize(PLAINS);
    }

    private List<PatternImage> initialize(PatternType patternType) {
        return resourceReader.read(patternType.getLocation())
                             .asFiles()
                             .map(file -> new PatternImage() {{
                                 try {
                                     fullImage     = readAllBytes(file.toPath());
                                     fullImageName = file.getName();
                                 } catch (IOException e) {
                                     e.printStackTrace();
                                 }
                             }})
                             .collect(toList());
    }

    @Resource(name = "resourceReader")
    public void setResourceReader(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
    }
}