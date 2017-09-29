package domain;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public interface Settings {
    /**
     * The values of ImageSize should be as close as possible to patterns average size, if they different.
     * The best way if all patterns have same width and height. Then set it in in WIDTH and HEIGHT and you are C00L :).
     */
    int INCOME_IMAGE_ALLOWED_MIN_WIDTH  = 80;
    int INCOME_IMAGE_ALLOWED_MIN_HEIGHT = 64;
    int INCOME_IMAGE_ALLOWED_MAX_WIDTH  = 5120;
    int INCOME_IMAGE_ALLOWED_MAX_HEIGHT = 4096;

    int PATTERN_WIDTH = 16;
    int PATTERN_HEIGHT = 16;

    Integer MIN_NUMBER_OF_EXPECTED_COLUMNS = 0;
    Integer MAX_NUMBER_OF_EXPECTED_COLUMNS = 256;

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
