package domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import utility.helper.ResourceReader;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration(locations = {
        "classpath:spring/basic-image-generator.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
//TODO: add more code coverage
public class BasicImageGeneratorTest {

    @Autowired
    private ImageGenerator imageGenerator;

    @Autowired
    private Settings settings;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        //TODO:add when->then for settings
    }

    @After
    public void tearDown() throws Exception {
        ResourceReader resourceReader = new ResourceReader();
        Optional.ofNullable(resourceReader.readFile("images/").listFiles())
                .ifPresent(filesArr -> Arrays.stream(filesArr)
                        .filter(file -> (file.getName().matches("^generate_image.+")))
                        .forEach(File::delete));
    }

    @Test
    public void generateImage() throws Exception {
        assertNotNull(imageGenerator);
        assertNotNull(settings);
        //TODO: add more conditions
    }
}