package layer.repository;

import domain.InformationalImage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Repository("galleryImageRepository")
public class GalleryImageRepositoryImpl implements GalleryImageRepository {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Integer getTotalCount() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM gallery_image;",
                Integer.class);
    }

    @Override
    public List<InformationalImage> getAllImages() {
        return jdbcTemplate.query("SELECT content FROM gallery_image" +
                        " WHERE content NOTNULL ORDER BY upload_date DESC;",
                (rs, rowNum) -> new InformationalImage(rs.getBytes(1)));
    }

    @Override
    public List<InformationalImage> get(Integer count) {
        return jdbcTemplate.query("SELECT content FROM gallery_image" +
                        " WHERE content NOTNULL ORDER BY upload_date DESC LIMIT ?;",
                new Object[] {count},
                (rs, rowNum) -> new InformationalImage(rs.getBytes(1)));
    }

    @Override
    public void add(InformationalImage newGalleryImage) {
        jdbcTemplate.update("INSERT INTO gallery_image (content) VALUES (?);",
                new Object[] {newGalleryImage.asByteArray()});
    }

    @Override
    public void replaceOldestBy(InformationalImage newGalleryImage) {
        jdbcTemplate.update("UPDATE gallery_image SET content = ?, upload_date = ?" +
                "WHERE upload_date = (SELECT min(upload_date) FROM gallery_image);",
                newGalleryImage.asByteArray(), new Date());
    }

    @Resource(name = "jdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
