package layers.repository;

import domain.InformationalImage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import system.ObjectTypeConverter;

import javax.sql.DataSource;
import java.util.List;

@Repository("galleryRepository")
public class GalleryRepositoryImpl implements GalleryRepository {

    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String DB_CONNECTION = "jdbc:postgresql://ec2-107-20-214-99.compute-1.amazonaws.com:5432/dfm6o5autn88hj?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    private static final String DB_USER = "mygtvnrgfvdksl";
    private static final String DB_PASSWORD = "6e059618a1ecb27ef2d05001c0721c042d786d97879d699c22c3b7537b9f2a3e";

    private static final DataSource DATA_SOURCE = new DriverManagerDataSource() {{
        setDriverClassName(DB_DRIVER);
        setUrl(DB_CONNECTION);
        setUsername(DB_USER);
        setPassword(DB_PASSWORD);
    }};

    @Override
    public InformationalImage get(String imageName) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DATA_SOURCE);
        return jdbcTemplate.query("SELECT image FROM images WHERE name=" + "\'" + imageName + "\'", (rs, rowNum) -> {
            ObjectTypeConverter typeConverter = new ObjectTypeConverter();
            return typeConverter.informationalImage(rs.getBytes(1));
        }).get(0);
    }

    @Override
    public List<InformationalImage> getAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DATA_SOURCE);
        return jdbcTemplate.query("SELECT image FROM images", (rs, rowNum) -> {
            ObjectTypeConverter typeConverter = new ObjectTypeConverter();
            return typeConverter.informationalImage(rs.getBytes(1));
        });
    }
}
