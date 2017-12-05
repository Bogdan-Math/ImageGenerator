package layers.repository;

import model.GalleryImage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@Repository("galleryImageRepository")
public class GalleryImageRepositoryImpl implements GalleryImageRepository {

    private DataSource dataSource;

    @Override
    public List<GalleryImage> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return jdbcTemplate.query("SELECT name, bytes FROM gallery_image",
                (rs, rowNum) -> new GalleryImage() {{
                    setName(rs.getString(1));
                    setBytes(rs.getBytes(2));
                }});
    }

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
