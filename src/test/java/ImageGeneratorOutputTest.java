import basic.ImageGenerator;
import basic.ObjectTypeConverter;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class ImageGeneratorOutputTest {

/*
    @Test
    @Ignore
    public void generateImages() throws Exception {
        imageGenerator.setExpectedColumnsNumber(250);
        assertTrue(generateImage(imageGenerator.setImage(ImageIO.read(resourceReader.readFile("images/chinese_garden.jpg"))), "images/chinese_garden_GEN.jpg"));
        assertTrue(generateImage(imageGenerator.setImage(ImageIO.read(resourceReader.readFile("images/cubes.jpg"))), "images/cubes_GEN.jpg"));
        assertTrue(generateImage(imageGenerator.setImage(ImageIO.read(resourceReader.readFile("images/jedi_sword.jpg"))), "images/jedi_sword_GEN.jpg"));
        assertTrue(generateImage(imageGenerator.setImage(ImageIO.read(resourceReader.readFile("images/music_man.jpg"))), "images/music_man_GEN.jpg"));
        assertTrue(generateImage(imageGenerator.setImage(ImageIO.read(resourceReader.readFile("images/puppy.jpg"))), "images/puppy_GEN.jpg"));
        assertTrue(generateImage(imageGenerator.setImage(ImageIO.read(resourceReader.readFile("images/skyline.jpg"))), "images/skyline_GEN.jpg"));
        assertTrue(generateImage(imageGenerator.setImage(ImageIO.read(resourceReader.readFile("images/smile.jpg"))), "images/smile_GEN.jpg"));
        assertTrue(generateImage(imageGenerator.setImage(ImageIO.read(resourceReader.readFile("images/test_image.jpg"))), "images/test_image_GEN.jpg"));
        assertTrue(generateImage(imageGenerator.setImage(ImageIO.read(resourceReader.readFile("images/wally_and_eva.jpg"))), "images/wally_and_eva_GEN.jpg"));
    }

    private Map<Color, BufferedImage> patterns(String resourcePath) {
        ImageGenerator imageGenerator = new ImageGenerator();
        ObjectTypeConverter objectTypeConverter = new ObjectTypeConverter();

        return Arrays.stream(Optional.ofNullable(resourceReader.readFile(resourcePath).listFiles())
                .orElseThrow(() -> new RuntimeException("Directory \'" + resourcePath + "\': is not exist or empty.")))
                .filter(File::isFile)
                .collect(Collectors
                        .toMap(
                                file -> imageGenerator.setImage(objectTypeConverter.bufferedImage(file)).averagedColor(),
                                objectTypeConverter::bufferedImage,
                                (img_color_1, img_color_2) -> {
                                    System.out.println("Two same average color: ");
                                    System.out.println(img_color_1);
                                    System.out.println(img_color_2);

                                    return img_color_1;
                                }
                        )
                );
    }

    private boolean generateImage(ImageGenerator imageGenerator, String outputName) throws IOException {
        ImageIO.write(imageGenerator.generateImage(), "jpg", resourceReader.readFile(outputName));
        return true;
    }
*/

}
