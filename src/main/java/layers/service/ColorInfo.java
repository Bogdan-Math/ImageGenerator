package layers.service;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface ColorInfo {
    Color averagedColor(BufferedImage image);
}