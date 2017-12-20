package layer.repository;

import model.GalleryImage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("galleryImageRepository")
public class GalleryImageRepositoryImpl implements GalleryImageRepository {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GalleryImage> getAllThumbnails() {
        return jdbcTemplate.query("SELECT thumbnail, thumbnail_name FROM gallery_image " +
                        "WHERE thumbnail NOTNULL",
                (rs, rowNum) -> new GalleryImage() {{
                    thumbnail     = rs.getBytes(1);
                    thumbnailName = rs.getString(2);
                }});
    }

    @Override
    public void save(GalleryImage galleryImage) {
        jdbcTemplate.update("INSERT INTO gallery_image (real, real_name, thumbnail, thumbnail_name) " +
                        "VALUES (?, ?, ?, ?)",
                galleryImage.real,
                galleryImage.realName,
                galleryImage.thumbnail,
                galleryImage.thumbnailName);
    }

    @Resource(name = "jdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
