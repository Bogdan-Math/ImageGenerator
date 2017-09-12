package domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

@Component
@Scope("session")
public class BasicSettings implements Settings {

    private BufferedImage image;
    private Map<Color, BufferedImage> patterns;
    private Integer expectedColumnsNumber;

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public Map<Color, BufferedImage> getPatterns() {
        return patterns;
    }

    @Override
    public void setPatterns(Map<Color, BufferedImage> patterns) {
        this.patterns = patterns;
    }

    @Override
    public Integer getExpectedColumnsNumber() {
        return expectedColumnsNumber;
    }

    @Override
    public void setExpectedColumnsNumber(Integer expectedColumnsNumber) {
        this.expectedColumnsNumber = expectedColumnsNumber;
    }

    @Override
    public int getImageWidth() {
        return getImage().getWidth();
    }

    @Override
    public int getImageHeight() {
        return getImage().getHeight();
    }

    @Override
    public BufferedImage getSubImage(int x, int y, int width, int height) {
        return getImage().getSubimage(x, y, width, height);
    }
}
