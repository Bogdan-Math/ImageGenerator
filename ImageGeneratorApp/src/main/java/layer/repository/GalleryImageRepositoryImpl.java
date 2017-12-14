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
        return jdbcTemplate.query("SELECT thumbnail_image, thumbnail_image_name FROM gallery_image " +
                        "WHERE thumbnail_image NOTNULL",
                (rs, rowNum) -> new GalleryImage() {{
                    thumbnailImage     = rs.getBytes(1);
                    thumbnailImageName = rs.getString(2);
                }});
    }

    @Override
    public void save(GalleryImage galleryImage) {
        jdbcTemplate.update("INSERT INTO gallery_image (full_image, full_image_name, thumbnail_image, thumbnail_image_name) " +
                        "VALUES (?, ?, ?, ?)",
                galleryImage.fullImage,
                galleryImage.fullImageName,
                galleryImage.thumbnailImage,
                galleryImage.thumbnailImageName);
    }

    @Resource(name = "jdbcTemplate")
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
