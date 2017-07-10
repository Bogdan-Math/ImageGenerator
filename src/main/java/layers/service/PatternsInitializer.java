package layers.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public interface PatternsInitializer {
    Map<Patterns, Map<Color, BufferedImage>> getPatterns();
}
