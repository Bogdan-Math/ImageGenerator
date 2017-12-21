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
    public Integer getImagesCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM gallery_image;", Integer.class);
    }

    @Override
    public List<InformationalImage> getAll() {
        return jdbcTemplate.query("SELECT content FROM gallery_image;",
                (rs, rowNum) -> new InformationalImage(rs.getBytes(1)));
    }

    @Override
    public void save(InformationalImage galleryImage) {
        jdbcTemplate.update("INSERT INTO gallery_image (content) VALUES (?);",
                new Object[] {galleryImage.asByteArray()});
    }

    @Override
    public void deleteOldest() {
        jdbcTemplate.update("DELETE FROM gallery_image " +
                "WHERE upload_date = (SELECT min(upload_date) FROM gallery_image);");
    }

    @Resource(name = "jdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
