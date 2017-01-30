package basic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ObjectTypeConverterTest {

    private File originalImage;
    private File generateImage;
    private File resourceFolder;

    private ObjectTypeConverter objectTypeConverter;

    @Before
    public void setUp() throws Exception {
        this.originalImage = createFile("puppy.jpg");
        this.generateImage = createFile("generate_image.jpg");
        this.resourceFolder = createFile("");

        this.objectTypeConverter = new ObjectTypeConverter();
    }

    @After
    public void tearDown() throws Exception {
        generateImage.delete();
    }

    @Test
    public void copyImage() throws Exception {
        assertEquals(new Long(1), getFilesCount());
        objectTypeConverter.copyImage(originalImage, generateImage, "jpg");
        assertEquals(new Long(2), getFilesCount());
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

    private Long getFilesCount() {
        return Arrays.stream(resourceFolder.list()).filter(resource -> createFile(resource).isFile()).count();
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("images/").getPath() + resourceName);
    }
}