package layers.repository;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public interface PatternsRepository {
    Map<Color, BufferedImage> getCommons();
    Map<Color, BufferedImage> getFlags();
    Map<Color, BufferedImage> getPlains();
}
