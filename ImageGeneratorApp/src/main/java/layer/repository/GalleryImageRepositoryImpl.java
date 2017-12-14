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
    public List<GalleryImage> getAll() {
        return jdbcTemplate.query("SELECT name, thumbnail_image FROM gallery_image WHERE thumbnail_image NOTNULL",
                (rs, rowNum) -> new GalleryImage() {{
                    setName(rs.getString(1));
                    setThumbnailImage(rs.getBytes(2));
                }});
    }

    @Override
    public void save(GalleryImage galleryImage) {
        jdbcTemplate.update("INSERT INTO gallery_image (name, full_image, thumbnail_image) VALUES (?, ?, ?)",
                galleryImage.getName(),
                galleryImage.getThumbnailImage(),
                galleryImage.getFullImage());
    }

    @Resource(name = "jdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
