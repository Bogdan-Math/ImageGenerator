package domain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public interface Settings {
    /**
     * The values of ImageSize should be as close as possible to patterns average size, if they different.
     * The best way if all patterns have same width and height. Then set it in in WIDTH and HEIGHT and you are C00L :).
     */
    int PATTERN_WIDTH = 14;//40;
    int PATTERN_HEIGHT = 14;//20;

    Integer MIN_NUMBER_OF_EXPECTED_COLUMNS = 0;
    Integer MAX_NUMBER_OF_EXPECTED_COLUMNS = 128;

    BufferedImage getImage();

    void setImage(BufferedImage image);

    Map<Color, BufferedImage> getPatterns();

    void setPatterns(Map<Color, BufferedImage> patterns);

    Integer getExpectedColumnsNumber();

    void setExpectedColumnsNumber(Integer expectedColumnsNumber);

    int getImageWidth();

    int getImageHeight();

    BufferedImage getSubImage(int x, int y, int width, int height);

    String getImageFileName();

    void setImageFileName(String imageFileName);
}
