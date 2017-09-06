package layers.service;

import utility.config.ImageGenerationConfig;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageGenerationService {

    //TODO: add descriptive comments to this method
    List<List<BufferedImage>> asMatrix(int expectedColumns);
    List<List<Color>> averagedColorsMatrix();
    List<List<BufferedImage>> resultMatrix();
    BufferedImage generateImage();

    //TODO: delete this method, after add Spring to tests
    void setConfig(ImageGenerationConfig config);
}
