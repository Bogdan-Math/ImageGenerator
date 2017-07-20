package layers.service;

import domain.PatternType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public interface PatternsService {
    Map<PatternType, Map<Color, BufferedImage>> getAllPatterns();
}
