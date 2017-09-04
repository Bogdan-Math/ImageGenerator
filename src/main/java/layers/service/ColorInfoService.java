package layers.service;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface ColorInfoService {

    Color averagedColor(BufferedImage image);
}