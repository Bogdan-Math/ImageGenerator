package basic;

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

import static org.junit.Assert.*;

public class ImageGeneratorTest {

    private BufferedImage bufferedImage;
    private ImageGenerator originalImageGenerator;

    private ImageGenerator chineseGarden;
    private ImageGenerator cubes;
    private ImageGenerator jediSword;
    private ImageGenerator musicMan;
    private ImageGenerator puppy;
    private ImageGenerator skyline;
    private ImageGenerator smile;
    private ImageGenerator testImageGenerator;
    private ImageGenerator wallyAndEva;

    @Before
    public void setUp() throws Exception {
        this.bufferedImage = ImageIO.read(createFile("images/chinese_garden.jpg"));
        this.originalImageGenerator = new ImageGenerator().workOn(bufferedImage);

        this.chineseGarden = new ImageGenerator().workOn(ImageIO.read(createFile("images/chinese_garden.jpg")));
        this.cubes = new ImageGenerator().workOn(ImageIO.read(createFile("images/cubes.jpg")));
        this.jediSword = new ImageGenerator().workOn(ImageIO.read(createFile("images/jedi_sword.jpg")));
        this.musicMan = new ImageGenerator().workOn(ImageIO.read(createFile("images/music_man.jpg")));
        this.puppy = new ImageGenerator().workOn(ImageIO.read(createFile("images/puppy.jpg")));
        this.skyline = new ImageGenerator().workOn(ImageIO.read(createFile("images/skyline.jpg")));
        this.smile = new ImageGenerator().workOn(ImageIO.read(createFile("images/smile.jpg")));
        this.testImageGenerator = new ImageGenerator().workOn(ImageIO.read(createFile("images/test_image.jpg")));
        this.wallyAndEva = new ImageGenerator().workOn(ImageIO.read(createFile("images/wally_and_eva.jpg")));
    }

    @After
    public void tearDown() throws Exception {
        Arrays.stream(createFile("images/").listFiles())
                .filter(file -> !(file.getName().equals("chinese_garden.jpg") || file.getName().equals("generate_image.jpg") ||

                                  file.getName().equals("chinese_garden.jpg") || file.getName().equals("chinese_garden_GEN.jpg") ||
                                  file.getName().equals("cubes.jpg") || file.getName().equals("cubes_GEN.jpg") ||
                                  file.getName().equals("jedi_sword.jpg") || file.getName().equals("jedi_sword_GEN.jpg") ||
                                  file.getName().equals("music_man.jpg") || file.getName().equals("music_man_GEN.jpg") ||
                                  file.getName().equals("puppy.jpg") || file.getName().equals("puppy_GEN.jpg") ||
                                  file.getName().equals("skyline.jpg") || file.getName().equals("skyline_GEN.jpg") ||
                                  file.getName().equals("smile.jpg") || file.getName().equals("smile_GEN.jpg") ||
                                  file.getName().equals("test_image.jpg") || file.getName().equals("test_image_GEN.jpg") ||
                                  file.getName().equals("wally_and_eva.jpg") || file.getName().equals("wally_and_eva_GEN.jpg") ||
                                  file.getName().equals("black.jpg") || file.getName().equals("black_GEN.jpg") ||
                                  file.getName().equals("me.jpg") || file.getName().equals("me_GEN.jpg")

                ))
                .forEach(File::delete);
    }

    @Test
    public void getBufferedImage() throws Exception {
        assertEquals(bufferedImage, originalImageGenerator.getBufferedImage());
    }

    @Test
    public void getCopy() throws Exception {

        BufferedImage original = originalImageGenerator.getBufferedImage();
        BufferedImage compared = originalImageGenerator.getCopy().getBufferedImage();

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

        BufferedImage original = originalImageGenerator.getBufferedImage();
        BufferedImage copied = originalImageGenerator.getSubImage(0, 0, 25, 25);

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

        List<List<BufferedImage>> matrix = originalImageGenerator.likeMatrix(x, y);

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

        assertEquals(Color.red, originalImageGenerator.workOn(originalImageGenerator.createRedImg(1, 1)).averageRGB());
        assertEquals(Color.green, originalImageGenerator.workOn(originalImageGenerator.createGreenImg(1, 1)).averageRGB());
        assertEquals(Color.blue, originalImageGenerator.workOn(originalImageGenerator.createBlueImg(1, 1)).averageRGB());
    }

    @Test
    public void averageRGBMatrix() {

        //TODO: add more conditions to check existing files
        originalImageGenerator.averageRGBMatrix(3, 3)
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
        assertEquals(255, (originalImageGenerator.createRedImg(1, 1).getRGB(0,0) & 0x00ff0000) >> 16);
    }

    @Test
    public void createGreenImg() {
        assertEquals(255, (originalImageGenerator.createGreenImg(1, 1).getRGB(0,0) & 0x0000ff00) >> 8);
    }

    @Test
    public void createBlueImg() {
        assertEquals(255, (originalImageGenerator.createBlueImg(1, 1).getRGB(0,0) & 0x000000ff));
    }

    @Test
    public void averageImgMatrix() {
        List<List<BufferedImage>> averageImgMatrix = originalImageGenerator.averageImgMatrix(8, 8);
        //TODO: add more conditions to check existing files
        averageImgMatrix.forEach(rows -> rows.forEach(square -> {
            try {
                ImageIO.write(square,
                        "jpg",
                        createFile("images/generate_image(" + rows.indexOf(square) + "," + averageImgMatrix.indexOf(rows) + ").jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

    }

    @Test
    public void averageImage() {
        try {
            ImageIO.write(originalImageGenerator.averageImage(100, 100),
                    "jpg",
                    createFile("images/generate_image.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void generateImages() throws Exception {
        generateImage(chineseGarden, "images/chinese_garden_GEN.jpg");
        generateImage(cubes, "images/cubes_GEN.jpg");
        generateImage(jediSword, "images/jedi_sword_GEN.jpg");
        generateImage(musicMan, "images/music_man_GEN.jpg");
        //generateImage(puppy, "images/puppy_GEN.jpg");
        generateImage(skyline, "images/skyline_GEN.jpg");
        generateImage(smile, "images/smile_GEN.jpg");
        generateImage(testImageGenerator, "images/test_image_GEN.jpg");
        generateImage(wallyAndEva, "images/wally_and_eva_GEN.jpg");
    }

    @Test
    public void generateImage1() throws Exception {
        BufferedImage image = flags().values().stream().max(Comparator.comparing(img -> img.getWidth() * img.getHeight())).orElse(null);
        System.out.println(image.getWidth() + ":" + image.getHeight());
        generateImage(puppy, "images/puppy_GEN.jpg");
    }

    private void generateImage(ImageGenerator inputImageGenerator, String outputName) throws IOException {
        List<List<Color>> matrix = inputImageGenerator
                .averageRGBMatrix(250, 300);
        Map<Color, BufferedImage> map = flags();

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

        ImageIO.write(originalImageGenerator.workOn(inputImageGenerator.getBufferedImage()).generateImageFrom(result), "jpg",
                    createFile(outputName));
    }

    public  Map<Color, BufferedImage> flags() {
        Map<Color, BufferedImage> map = new HashMap<>();
        ImageGenerator imageGenerator = new ImageGenerator();
        ObjectTypeConverter objectTypeConverter = new ObjectTypeConverter();
        Arrays.stream(createFile("images/flags/").listFiles())
                .filter(File::isFile)
                .forEach(flag -> {
                    Color color = null;
                    BufferedImage bufferedImage = null;
                    try {
                        bufferedImage = objectTypeConverter.bufferedImageFromFile(flag);
                        color = imageGenerator.workOn(bufferedImage).averageRGB();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    map.put(color, bufferedImage);
                });
        return map;
    }


    private Map<Color, BufferedImage> patterns() {
        Map<Color, BufferedImage> map = new HashMap<>();
        ImageGenerator imageGenerator = new ImageGenerator();

        try {
            BufferedImage p1 = ImageIO.read(createFile("images/patterns/p1.jpg"));
            BufferedImage p2 = ImageIO.read(createFile("images/patterns/p2.jpg"));
            BufferedImage p3 = ImageIO.read(createFile("images/patterns/p3.jpg"));
            BufferedImage p4 = ImageIO.read(createFile("images/patterns/p4.jpg"));
            BufferedImage p5 = ImageIO.read(createFile("images/patterns/p5.jpg"));
            BufferedImage p6 = ImageIO.read(createFile("images/patterns/p6.jpg"));
            BufferedImage p7 = ImageIO.read(createFile("images/patterns/p7.jpg"));
            BufferedImage p8 = ImageIO.read(createFile("images/patterns/p8.jpg"));
            BufferedImage p9 = ImageIO.read(createFile("images/patterns/p9.jpg"));
            BufferedImage p10 = ImageIO.read(createFile("images/patterns/p10.jpg"));

            map.put(imageGenerator.workOn(p1).averageRGB(), p1);
            map.put(imageGenerator.workOn(p2).averageRGB(), p2);
            map.put(imageGenerator.workOn(p3).averageRGB(), p3);
            map.put(imageGenerator.workOn(p4).averageRGB(), p4);
            map.put(imageGenerator.workOn(p5).averageRGB(), p5);
            map.put(imageGenerator.workOn(p6).averageRGB(), p6);
            map.put(imageGenerator.workOn(p7).averageRGB(), p7);
            map.put(imageGenerator.workOn(p8).averageRGB(), p8);
            map.put(imageGenerator.workOn(p9).averageRGB(), p9);
            map.put(imageGenerator.workOn(p10).averageRGB(), p10);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }


    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }


}