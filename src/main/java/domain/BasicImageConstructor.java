package domain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class BasicImageConstructor implements ImageConstructor {

    @Override
    public List<List<BufferedImage>> asMatrix(BufferedImage image, int expectedColumn) {
        return null;
    }

    @Override
    public List<List<Color>> averagedColorsMatrix() {
        return null;
    }

    @Override
    public BufferedImage generateImage() {
        return null;
    }
}
