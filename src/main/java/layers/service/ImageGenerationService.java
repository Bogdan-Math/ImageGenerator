package layers.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public interface ImageGenerationService {

    BufferedImage getImage();
    void setImage(BufferedImage image);
    Map<Color, BufferedImage> getPatterns();
    void setPatterns(Map<Color, BufferedImage> patterns);
    Integer getExpectedColumnsNumber();
    void setExpectedColumnsNumber(Integer expectedColumnsNumber);

    //TODO: add descriptive comments to this method
    List<List<BufferedImage>> asMatrix(int expectedColumns);
    List<List<Color>> averagedColorsMatrix();
    List<List<BufferedImage>> resultMatrix();
    BufferedImage generateImage();
}
