package layer.repository;

import domain.InformationalImage;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import system.ResourceReader;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Ignore
public class PostgreSQLTest {

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

    class GalleryImage {

        String name;
        byte[] bytes;

        public void setName(String name) {
            this.name = name;
        }

        public void setBytes(byte[] bytes) {
            this.bytes = bytes;
        }

        public String getName() {
            return name;
        }

        public byte[] getBytes() {
            return bytes;
        }
    }

    @Test
    public void a1() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DATA_SOURCE);
        jdbcTemplate.execute("DELETE FROM gallery_image");
    }

    @Test
    public void name() throws Exception {

        ResourceReader resourceReader = new ResourceReader();
        List<GalleryImage> images = resourceReader.readAllIn("1")
                .asByteArrays()
                .map(byteArray -> new GalleryImage() {{
                    try {
                        setBytes(new InformationalImage(byteArray).resizeTo(250, 250).asByteArray());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }}).collect(toList());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(DATA_SOURCE);
//        jdbcTemplate.update("INSERT INTO gallery_image (name, bytes) VALUES (?, ?)", galleryImage.getName(), galleryImage.getBytes());
        jdbcTemplate.batchUpdate("INSERT INTO gallery_image (name, bytes) VALUES (?, ?)", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                GalleryImage image = images.get(i);
                preparedStatement.setString(1, image.getName());
                preparedStatement.setBytes(2, image.getBytes());
            }

            @Override
            public int getBatchSize() {
                return images.size();
            }
        });
    }

}
