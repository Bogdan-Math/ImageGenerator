package basic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utility.FileReader;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ObjectTypeConverterTest {

    private FileReader fileReader;

    private File originalImage;
    private File generateImage;

    private ObjectTypeConverter objectTypeConverter;

    @Before
    public void setUp() throws Exception {
        this.fileReader = new FileReader();
        this.originalImage = fileReader.getFileObject("images/canonical.jpg");
        this.generateImage = fileReader.getFileObject("images/canonical_GEN.jpg");

        this.objectTypeConverter = new ObjectTypeConverter();
    }

    @After
    public void tearDown() throws Exception {
        Arrays.stream(fileReader.getFileObject("images/").listFiles())
                .filter(file -> ("canonical_GEN.jpg".equals(file.getName())))
                .forEach(File::delete);
    }

    @Test
    public void copyImage() throws Exception {
        assertEquals(1, allFilesIn().stream().filter(file -> (file.getName().matches("^canonical.+"))).count());
        objectTypeConverter.copyImage(originalImage, generateImage, "jpg");
        assertEquals(2, allFilesIn().stream().filter(file -> (file.getName().matches("^canonical.+"))).count());
    }

    @Test(expected = RuntimeException.class)
    public void copyImageException() throws Exception {
        objectTypeConverter.copyImage(new File("WRONG_PATH"), new File("WRONG_PATH"), "jpg");
    }

    @Test
    public void bufferedImageFromFile() throws Exception {
        assertNotNull(objectTypeConverter.bufferedImageFromFile(originalImage));
    }

    @Test(expected = RuntimeException.class)
    public void bufferedImageFromFileException() throws Exception {
        assertNotNull(objectTypeConverter.bufferedImageFromFile(new File("WRONG_PATH")));
    }

    @Test
    public void inputStreamFromBufferedImage() throws Exception {
        assertNotNull(objectTypeConverter
                .inputStream(objectTypeConverter.bufferedImageFromFile(originalImage), "jpg"));
    }

    @Test(expected = RuntimeException.class)
    public void inputStreamFromBufferedImageException() throws Exception {
        assertNotNull(objectTypeConverter
                .inputStream(null, "jpg"));
    }

    @Test
    public void bufferedImageFromByteArray() throws Exception {
        assertNotNull(objectTypeConverter
                .bufferedImageFromInputStream(objectTypeConverter
                        .inputStream(objectTypeConverter.bufferedImageFromFile(originalImage), "jpg")));
    }

    @Test(expected = RuntimeException.class)
    public void bufferedImageFromByteArrayException() throws Exception {
        assertNotNull(objectTypeConverter
                .bufferedImageFromInputStream(null));
    }

    private List<File> allFilesIn() {
        return Arrays.stream(fileReader.getFileObject("images/").listFiles())
                .filter(File::isFile)
                .collect(Collectors.toList());
    }
}