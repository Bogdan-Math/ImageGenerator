package domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

@Component
@Scope("session")
public class ImageGeneratorSettings {
    //TODO: add implementation

    /**
     * The values of ImageSize should be as close as possible to patterns average size, if they different.
     * The best way if all patterns have same width and height. Then set it in in WIDTH and HEIGHT and you are C00L :).
     */
    public static final int PATTERN_WIDTH = 14;//40;
    public static final int PATTERN_HEIGHT = 14;//20;

    private BufferedImage image;
    private Map<Color, BufferedImage> patterns;
    private Integer expectedColumnsNumber;

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Map<Color, BufferedImage> getPatterns() {
        return patterns;
    }

    public void setPatterns(Map<Color, BufferedImage> patterns) {
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
}
