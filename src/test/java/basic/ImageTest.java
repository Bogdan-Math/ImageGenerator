package basic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ImageTest {

    private BufferedImage bufferedImage;

    private Image redlImage;
    private Image greenImage;
    private Image blueImage;
    private Image originalImage;

    @Before
    public void setUp() throws Exception {
        this.bufferedImage = ImageIO.read(createFile("images/original_image.jpg"));

        this.redlImage = new Image(ImageIO.read(createFile("images/rgb/red.jpg")));
/*
        this.greenImage = new Image(ImageIO.read(createFile("images/rgb/green.jpg")));
        this.blueImage = new Image(ImageIO.read(createFile("images/rgb/blue.jpg")));
*/
        this.originalImage = new Image(bufferedImage);
    }

    @After
    public void tearDown() throws Exception {
        Arrays.stream(createFile("images/").listFiles())
                .filter(file -> !file.getName().equals("original_image.jpg"))
                .forEach(File::delete);
    }

    @Test
    public void getBufferedImage() throws Exception {
        assertEquals(bufferedImage, originalImage.getBufferedImage());
    }

    @Test
    public void getCopy() throws Exception {

        BufferedImage original = originalImage.getBufferedImage();
        BufferedImage compared = originalImage.getCopy().getBufferedImage();

        assertNotEquals(original, compared);
        assertEquals(original.getWidth(), compared.getWidth());
        assertEquals(original.getWidth(), compared.getWidth());

        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                assertEquals(original.getRGB(x, y), compared.getRGB(x, y));
            }
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getSubImage() throws Exception {

        BufferedImage original = originalImage.getBufferedImage();
        BufferedImage copied = originalImage.getSubImage(0, 0, 25, 25);

        assertNotEquals(original, copied);
        assertNotEquals(original.getWidth(), copied.getWidth());
        assertNotEquals(original.getWidth(), copied.getWidth());

        for (int y = 0; y < copied.getHeight(); y++) {
            for (int x = 0; x < copied.getWidth(); x++) {
                assertEquals(original.getRGB(x, y), copied.getRGB(x, y));
            }
        }

        copied.getRGB(original.getWidth(), original.getHeight());
    }

    @Test
    public void likeMatrix() {
        int x = 3;
        int y = 7;

        List<List<BufferedImage>> matrix = originalImage.likeMatrix(x, y);

        assertEquals(x, matrix.size());
        assertEquals(y, matrix.get(0).size());
        assertEquals(x * y, matrix.size() * matrix.get(0).size());

        //TODO: add more conditions to check existing files
        matrix.forEach(rows -> rows.forEach(square -> {
            try {
                ImageIO.write(square,
                        "jpg",
                        createFile("images/generate_image(" + rows.indexOf(square) + "," + matrix.indexOf(rows) + ").jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    @Test
    public void averageRGB() {

        BufferedImage redImg = originalImage.getRedImg(800, 600);
        int rgb = new Image(redImg).averageRGB();

        int  r = (rgb & 0x00ff0000) >> 16;
        int  g = (rgb & 0x0000ff00) >> 8;
        int  b =  rgb & 0x000000ff;
        System.out.println(r + ", " + g + ", " + b);
    }

    @Test
    public void averageRGBMatrix() {

        try {
            ImageIO.write(originalImage.getRedImg(800, 600), "jpg", createFile("images/rgb/red.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        originalImage.averageRGBMatrix(3, 7)
                .forEach(row -> {row
                        .forEach(averageRGB -> {
                            int r = (averageRGB>>16)&0xFF;
                            int g = (averageRGB>>8)&0xFF;
                            int b = (averageRGB>>0)&0xFF;
                            System.out.print("(" + r + ", " + g + ", " + b + ")");
                        });
                    System.out.println();
                });
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }


}