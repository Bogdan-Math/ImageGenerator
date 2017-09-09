package domain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageGenerator {

    void setSettings(Settings settings);

    //TODO: add descriptive comments to this method
    List<List<BufferedImage>> asMatrix();
    List<List<Color>> averagedColorsMatrix();
    List<List<BufferedImage>> resultMatrix();
    BufferedImage generateImage();
}
