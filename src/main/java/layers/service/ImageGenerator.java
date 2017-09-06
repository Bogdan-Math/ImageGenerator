package layers.service;

import utility.config.ImageGenerationConfig;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageGenerator {

    //TODO: add descriptive comments to this method
    List<List<BufferedImage>> asMatrix(int expectedColumns);
    List<List<Color>> averagedColorsMatrix();
    List<List<BufferedImage>> resultMatrix();
    BufferedImage generateImage();

    void setConfig(ImageGenerationConfig config);
}
