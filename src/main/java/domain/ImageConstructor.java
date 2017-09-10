package domain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageConstructor {

    //TODO: add descriptive comments to this method
    List<List<BufferedImage>> asMatrix(BufferedImage image, int expectedColumn);
    List<List<Color>> averagedColorsMatrix();
    BufferedImage generateImage();

}
