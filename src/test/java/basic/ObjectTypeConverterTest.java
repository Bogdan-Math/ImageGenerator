package basic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ObjectTypeConverterTest {

    private File originalImage;
    private File generateImage;

    private ObjectTypeConverter objectTypeConverter;

    @Before
    public void setUp() throws Exception {
        this.originalImage = createFile("images/canonical.jpg");
        this.generateImage = createFile("images/canonical_GEN.jpg");

        this.objectTypeConverter = new ObjectTypeConverter();
    }

    @After
    public void tearDown() throws Exception {
        Arrays.stream(createFile("images/").listFiles())
                .filter(file -> ("canonical_GEN.jpg".equals(file.getName())))
                .forEach(File::delete);
    }

    @Test
    public void copyImage() throws Exception {
        assertEquals(1, allFilesIn().stream().filter(file -> (file.getName().matches("^canonical.+"))).count());
        objectTypeConverter.copyImage(originalImage, generateImage, "jpg");
        assertEquals(2, allFilesIn().stream().filter(file -> (file.getName().matches("^canonical.+"))).count());
    }

    @Test
    public void bufferedImageFromFile() throws Exception {
        assertNotNull(objectTypeConverter.bufferedImageFromFile(originalImage));
    }

    @Test
    public void byteArrayFromBufferedImage() throws Exception {
        assertNotNull(objectTypeConverter
                .byteArrayFromBufferedImage(objectTypeConverter.bufferedImageFromFile(originalImage), "jpg"));
    }

    @Test
    public void bufferedImageFromByteArray() throws Exception {
        assertNotNull(objectTypeConverter
                .bufferedImageFromByteArray(objectTypeConverter
                        .byteArrayFromBufferedImage(objectTypeConverter.bufferedImageFromFile(originalImage), "jpg")));
    }

    private List<File> allFilesIn() {
        return Arrays.stream(createFile("images/").listFiles())
                .filter(File::isFile)
                .collect(Collectors.toList());
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }
}