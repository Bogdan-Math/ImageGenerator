package layers.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public interface PatternsService {
    Map<PatternsType, Map<Color, BufferedImage>> getAllPatterns();
}
