package basic;

import exceptions.ExpectedMatrixSizeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utility.FileReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ImageGeneratorTest {

    private FileReader fileReader;

    private ImageGenerator imageGenerator;

    private BufferedImage canonicalImage;
    private BufferedImage whiteImage;
    private BufferedImage grayImage;
    private BufferedImage blackImage;

    private Map<Color, BufferedImage> patterns;
    private Integer expectedColumnsNumber;

    @Before
    public void setUp() throws Exception {
        this.fileReader = new FileReader();

        this.canonicalImage = ImageIO.read(fileReader.getFileObject("images/canonical.jpg"));
        this.whiteImage = ImageIO.read(fileReader.getFileObject("images/colors/1-white.jpg"));
        this.grayImage = ImageIO.read(fileReader.getFileObject("images/colors/2-grey.jpg"));
        this.blackImage = ImageIO.read(fileReader.getFileObject("images/colors/3-black.jpg"));

        this.patterns = patterns("images/colors");
        this.expectedColumnsNumber = 300;

        this.imageGenerator = new ImageGenerator()
                .setImage(canonicalImage)
                .setPatterns(patterns)
                .setExpectedColumnsNumber(expectedColumnsNumber);
    }

    @After
    public void tearDown() throws Exception {
        Optional.ofNullable(fileReader.getFileObject("images/").listFiles())
                .ifPresent(filesArr -> Arrays.stream(filesArr)
                        .filter(file -> (file.getName().matches("^generate_image.+")))
                        .forEach(File::delete));
    }

    @Test
    public void averagedColor() throws Exception {
        assertEquals(Color.WHITE, imageGenerator.setImage(whiteImage).averagedColor());
        assertEquals(Color.GRAY, imageGenerator.setImage(grayImage).averagedColor());
        assertEquals(Color.BLACK, imageGenerator.setImage(blackImage).averagedColor());
    }

    @Test(expected = ExpectedMatrixSizeException.class)
    public void likeMatrix() throws Exception {
        int expectedColumn = 100;

        List<List<BufferedImage>> matrix = imageGenerator.setImage(canonicalImage).likeMatrix(expectedColumn);
        int matrixWidth = matrix.size();
        int matrixHeight = matrix.get(0).size();

        assertTrue(expectedColumn <= matrixWidth); // matrix width SHOULD BE more or equals then expected columns number.
        assertTrue(expectedColumn >= matrixHeight); // matrix height SHOULD BE less or equals then expected columns number.
        assertTrue(matrixWidth <= canonicalImage.getWidth()); // matrix width CAN NOT be more then image width.
        assertTrue(matrixHeight <= canonicalImage.getHeight()); // matrix height CAN NOT be more then image height.

        imageGenerator.setImage(canonicalImage).likeMatrix(2000);
    }

    @Test
    public void averagedColorsMatrix() throws Exception {
        int white = 255;
        imageGenerator.setImage(whiteImage).setExpectedColumnsNumber(10)
                .averagedColorsMatrix()
                .forEach(row -> row
                        .forEach(averageRGB -> {
                            assertEquals(white, averageRGB.getRed());
                            assertEquals(white, averageRGB.getGreen());
                            assertEquals(white, averageRGB.getBlue());
                        }));
    }

    @Test
    public void generateResultMatrix() throws Exception {
        imageGenerator.setExpectedColumnsNumber(1000)
                .generateResultMatrix()
                .forEach(row -> row
                        .forEach(patternImg -> {
                            assertTrue(patterns.values().contains(patternImg));
                        }));
    }

    @Test
    public void makeImage() throws Exception {
        BufferedImage generatedImage = imageGenerator.setImage(whiteImage).setExpectedColumnsNumber(10).makeImage();
        assertNotEquals(whiteImage, generatedImage);
        assertTrue(generatedImage.getWidth() >= whiteImage.getWidth());
        assertTrue(generatedImage.getHeight() >= whiteImage.getHeight());
    }

    @Test
    public void generateImages() throws Exception {
        imageGenerator.setExpectedColumnsNumber(200);
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.getFileObject("images/chinese_garden.jpg"))), "images/chinese_garden_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.getFileObject("images/cubes.jpg"))), "images/cubes_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.getFileObject("images/jedi_sword.jpg"))), "images/jedi_sword_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.getFileObject("images/music_man.jpg"))), "images/music_man_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.getFileObject("images/puppy.jpg"))), "images/puppy_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.getFileObject("images/skyline.jpg"))), "images/skyline_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.getFileObject("images/smile.jpg"))), "images/smile_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.getFileObject("images/test_image.jpg"))), "images/test_image_GEN.jpg");
        generateImage(imageGenerator.setImage(ImageIO.read(fileReader.getFileObject("images/wally_and_eva.jpg"))), "images/wally_and_eva_GEN.jpg");
    }

    private void generateImage(ImageGenerator imageGenerator, String outputName) throws IOException {
        ImageIO.write(imageGenerator.makeImage(), "jpg", fileReader.getFileObject(outputName));
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

        return Arrays.stream(Optional.ofNullable(fileReader.getFileObject(resourcePath).listFiles())
                .orElseThrow(() -> new RuntimeException("Directory \'" + resourcePath + "\': is not exist or empty.")))
                .filter(File::isFile)
                .collect(Collectors
                        .toMap(
                                file -> imageGenerator.setImage(objectTypeConverter.bufferedImageFromFile(file)).averagedColor(),
                                objectTypeConverter::bufferedImageFromFile,
                                (img_color_1, img_color_2) -> {
                                    System.out.println("Two same average color: ");
                                    System.out.println(img_color_1);
                                    System.out.println(img_color_2);

                                    return img_color_1;
                                }
                        )
                );
    }
}