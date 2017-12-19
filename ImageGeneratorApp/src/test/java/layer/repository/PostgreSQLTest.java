package layer.repository;

import model.PatternImage;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    public void a1() throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(DATA_SOURCE);
        jdbcTemplate.execute("DELETE FROM gallery_image");
    }

    @Test
    public void name() throws Exception {

        List<PatternImage> list = Stream.of("ua.png", "us.png").map(imgName -> new PatternImage() {{
            name = imgName;
            path = "images/flags";
        }}).collect(Collectors.toList());

        new JdbcTemplate(DATA_SOURCE)
                .batchUpdate("INSERT INTO pattern_image (name, path) VALUES (?, ?)", new BatchPreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                        PatternImage patternImage = list.get(i);
                        preparedStatement.setString(1, patternImage.name);
                        preparedStatement.setString(2, patternImage.path);
                    }

                    @Override
                    public int getBatchSize() {
                        return list.size();
                    }
                });
    }

}
