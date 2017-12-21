package layer.repository;

import domain.InformationalImage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("galleryImageRepository")
public class GalleryImageRepositoryImpl implements GalleryImageRepository {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<InformationalImage> getAll() {
        return jdbcTemplate.query("SELECT content FROM gallery_image",
                (rs, rowNum) -> new InformationalImage(rs.getBytes(1)));
    }

    @Override
    public void save(InformationalImage galleryImage) {
        jdbcTemplate.update("INSERT INTO gallery_image (content) VALUES (?)",
                new Object[] {galleryImage.asByteArray()});
    }

    @Resource(name = "jdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
