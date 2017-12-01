package layers.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.io.IOException;

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

    class ImgFile {

        String name;
        byte[] bytes;

        ImgFile(String name, byte[] bytes) {
            this.name = name;
            this.bytes = bytes;
        }
    }

    @Test
    public void name() throws Exception {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(DATA_SOURCE);
        jdbcTemplate.query("SELECT name, image FROM images", (rs, rowNum) ->
                new ImgFile(rs.getString(1), rs.getBytes(2)))
        .forEach(imgFile -> {
            try {
                new FileOutputStream(imgFile.name).write(imgFile.bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
