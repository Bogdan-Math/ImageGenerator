package basic;

import utility.FileReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ImageGeneratorTest {

    private FileReader fileReader;

    private ImageGenerator imageGenerator;

    private BufferedImage canonicalImage;
    private Map<Color, BufferedImage> patterns;
    private Integer expectedColumnsNumber;

    @Before
    public void setUp() throws Exception {
        this.fileReader = new FileReader();
        this.canonicalImage = ImageIO.read(fileReader.read("images/canonical.jpg"));
        this.patterns = patterns("images/colors");
        this.expectedColumnsNumber = 100;

        this.imageGenerator = new ImageGenerator()
                .setImage(canonicalImage)
                .setPatterns(patterns)
                .setExpectedColumnsNumber(expectedColumnsNumber);
    }

    @After
    public void tearDown() throws Exception {
        Arrays.stream(fileReader.read("images/").listFiles())
                .filter(file -> (file.getName().matches("^generate_image.+")))
                .forEach(File::delete);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getSubImage() throws Exception {

        BufferedImage original = imageGenerator.getImage();
        BufferedImage copied = imageGenerator.getSubImage(0, 0, 25, 25);

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

        List<List<BufferedImage>> matrix = imageGenerator.setImage(canonicalImage).likeMatrix(x);

        assertTrue(x <= matrix.size());

        //TODO: add more conditions to check existing files
        matrix.forEach(rows -> rows.forEach(square -> {
            try {
                ImageIO.write(square,
                        "jpg",
                        fileReader.read("images/generate_image(" + rows.indexOf(square) + "," + matrix.indexOf(rows) + ").jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    @Test
    public void averageRGB() {
        assertEquals(Color.red, imageGenerator.setImage(imageGenerator.createRedImg(1, 1)).averageRGB());
        assertEquals(Color.green, imageGenerator.setImage(imageGenerator.createGreenImg(1, 1)).averageRGB());
        assertEquals(Color.blue, imageGenerator.setImage(imageGenerator.createBlueImg(1, 1)).averageRGB());
    }

    @Test
    public void averageRGBMatrix() {

        //TODO: add more conditions to check existing files
        imageGenerator.averageRGBMatrix(3)
                .forEach(row -> {row
                        .forEach(averageRGB -> {
                            int r = averageRGB.getRed();
                            int g = averageRGB.getGreen();
                            int b = averageRGB.getBlue();
                            System.out.print("(" + r + ", " + g + ", " + b + ")");
                        });
                    System.out.println();
                });
    }

    @Test
    public void createRedImg() {
        assertEquals(255, (imageGenerator.createRedImg(1, 1).getRGB(0,0) & 0x00ff0000) >> 16);
    }

    @Test
    public void createGreenImg() {
        assertEquals(255, (imageGenerator.createGreenImg(1, 1).getRGB(0,0) & 0x0000ff00) >> 8);
    }

    @Test
    public void createBlueImg() {
        assertEquals(255, (imageGenerator.createBlueImg(1, 1).getRGB(0,0) & 0x000000ff));
    }

    @Test
    public void averageImgMatrix() {
        List<List<BufferedImage>> averageImgMatrix = imageGenerator.averageImgMatrix(8, 8);
        //TODO: add more conditions to check existing files
        averageImgMatrix.forEach(rows -> rows.forEach(square -> {
            try {
                ImageIO.write(square,
                        "jpg",
                        fileReader.read("images/generate_image(" + rows.indexOf(square) + "," + averageImgMatrix.indexOf(rows) + ").jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

    }

    @Test
    public void averageImage() throws Exception {
            ImageIO.write(imageGenerator.averageImage(100, 100),
                    "jpg",
                    fileReader.read("images/generate_image.jpg"));
    }

    @Test
    public void generateImages() throws Exception {
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.read("images/chinese_garden.jpg"))), "images/chinese_garden_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.read("images/cubes.jpg"))), "images/cubes_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.read("images/jedi_sword.jpg"))), "images/jedi_sword_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.read("images/music_man.jpg"))), "images/music_man_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.read("images/puppy.jpg"))), "images/puppy_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.read("images/skyline.jpg"))), "images/skyline_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.read("images/smile.jpg"))), "images/smile_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.read("images/test_image.jpg"))), "images/test_image_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.read("images/wally_and_eva.jpg"))), "images/wally_and_eva_GEN.jpg");
    }

    @Test
    public void generateSomeImage() throws Exception {
        BufferedImage image = patterns("images/flags/").values().stream().max(Comparator.comparing(img -> img.getWidth() * img.getHeight())).orElse(null);
        System.out.println(image.getWidth() + ":" + image.getHeight());
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.read("images/canonical.jpg"))), "images/canonical_GEN.jpg");
    }

    //TODO: bring it method to ImageGenerator
    private void generateImage(ImageGenerator imageGenerator, String outputName) throws IOException {
        List<List<Color>> matrix         = imageGenerator.averageRGBMatrix(200);
        Map<Color, BufferedImage> map    = imageGenerator.getPatterns();
        List<List<BufferedImage>> result = new ArrayList<>();

        for (List<Color> colors : matrix) {
            List<BufferedImage> resultRows = new ArrayList<>();

            for (Color color : colors) {
                int minColor = Integer.MAX_VALUE;
                BufferedImage minImg = null;
                for (Color pColor : map.keySet()) {
                    int dColor = Math.abs(color.getRed() - pColor.getRed()) +
                                 Math.abs(color.getGreen() - pColor.getGreen()) +
                                 Math.abs(color.getBlue() - pColor.getBlue());
                    if (dColor < minColor) {
                        minColor = dColor;
                        minImg = map.get(pColor);
                    }
                }
                resultRows.add(minImg);

            }
            result.add(resultRows);
        }
        ImageIO.write(this.imageGenerator.setImage(imageGenerator.getImage()).generateImageFrom(result), "jpg",
                fileReader.read(outputName));
    }

    @Test
    public void getAndSetImage() throws Exception {
        assertEquals(canonicalImage, imageGenerator.setImage(canonicalImage).getImage());
    }

    @Test
    public void getAndSetPatterns() throws Exception {
        assertEquals(patterns, imageGenerator.setPatterns(patterns).getPatterns());
    }

    @Test
    public void getAndSetExpectedColumnsNumber() throws Exception {
        assertEquals(expectedColumnsNumber, imageGenerator.setExpectedColumnsNumber(expectedColumnsNumber).getExpectedColumnsNumber());
    }

    private Map<Color, BufferedImage> patterns(String resourcePath) {
        ImageGenerator imageGenerator = new ImageGenerator();
        ObjectTypeConverter objectTypeConverter = new ObjectTypeConverter();

        return Arrays.stream(fileReader.read(resourcePath).listFiles())
                .filter(File::isFile)
                .collect(Collectors
                        .toMap(
                                file -> imageGenerator.setImage(objectTypeConverter.bufferedImageFromFile(file)).averageRGB(),
                                objectTypeConverter::bufferedImageFromFile,
                                (img_color_1, img_color_2) -> {
                                    System.out.println("Two same average color:");
                                    System.out.println(img_color_1);
                                    System.out.println(img_color_2);

                                    return img_color_1;
                                }
                        )
                );
    }
}