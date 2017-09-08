package domain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public interface ImageGenerator {

    void setSettings(ImageGeneratorSettings settings);

    //TODO: add descriptive comments to this method
    List<List<BufferedImage>> asMatrix();
    List<List<Color>> averagedColorsMatrix();
    List<List<BufferedImage>> resultMatrix();
    BufferedImage generateImage();
}
