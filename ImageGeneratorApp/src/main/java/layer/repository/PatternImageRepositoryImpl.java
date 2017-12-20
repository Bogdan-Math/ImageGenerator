package layer.repository;

import domain.PatternType;
import model.PatternImage;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import system.ResourceReader;

import javax.annotation.Resource;
import java.util.stream.Stream;

import static domain.PatternType.*;

@Scope("singleton")
@Repository("patternImageRepository")
public class PatternImageRepositoryImpl implements PatternImageRepository {

    private ResourceReader resourceReader;
    private JdbcTemplate jdbcTemplate;

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

        String location = jdbcTemplate.queryForObject("SELECT location FROM pattern WHERE type = ?",
                new Object[] {patternType.name().replaceFirst(".$", "")},
                String.class);

        return resourceReader.readAllIn(location)
                .asByteArrays()
                .map(PatternImage::new);
    }

    @Resource(name = "resourceReader")
    public void setResourceReader(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
    }

    @Resource(name = "jdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}