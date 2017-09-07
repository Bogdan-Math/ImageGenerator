package utility.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utility.helpers.ImageInformation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ImageGenerationConfig {

    @Autowired
    private ImageInformation imageInformation;

    /**
     * The values of ImageSize should be as close as possible to patterns average size, if they different.
     * The best way if all patterns have same width and height. Then set it in in WIDTH and HEIGHT and you are C00L :).
     */
    public static final int PATTERN_WIDTH = 14;//40;
    public static final int PATTERN_HEIGHT = 14;//20;

    private BufferedImage image;
    private List<BufferedImage> patterns;
    private Integer expectedColumnsNumber;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public List<BufferedImage> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<BufferedImage> patterns) {
        this.patterns = patterns;
    }

    public Integer getExpectedColumnsNumber() {
        return expectedColumnsNumber;
    }

    public void setExpectedColumnsNumber(Integer expectedColumnsNumber) {
        this.expectedColumnsNumber = expectedColumnsNumber;
    }

    public int getImageWidth() {
        return image.getWidth();
    }

    public int getImageHeight() {
        return image.getHeight();
    }

    public BufferedImage getSubImage(int x, int y, int width, int height) {
        return image.getSubimage(x, y, width, height);
    }

    public Map<Color, BufferedImage> patternsMap() {
        return patterns.stream()
                .collect(Collectors
                        .toMap(
                                image -> imageInformation.averagedColor(image), // put Color         as KEY   in map
                                image -> image,                                 // put BufferedImage as VALUE in map

                                (img_color_1, img_color_2) -> {
                                    System.out.println("Two same average color: ");
                                    System.out.println(img_color_1);
                                    System.out.println(img_color_2);

                                    return img_color_1;
                                }
                        )
                );
    }

    public void setImageInformation(ImageInformation imageInformation) {
        this.imageInformation = imageInformation;
    }
}