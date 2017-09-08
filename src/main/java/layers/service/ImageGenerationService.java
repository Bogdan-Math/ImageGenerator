package layers.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public interface ImageGenerationService {

    void setImage(BufferedImage image);
    void setPatterns(Map<Color, BufferedImage> patterns);
    void setExpectedColumnsNumber(Integer expectedColumnsNumber);

    //TODO: add descriptive comments to this method
    List<List<BufferedImage>> asMatrix();
    List<List<Color>> averagedColorsMatrix();
    List<List<BufferedImage>> resultMatrix();
    BufferedImage generateImage();
}
