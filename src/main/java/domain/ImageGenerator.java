package domain;

import java.awt.image.BufferedImage;

public interface ImageGenerator {

    void setSettings(Settings settings);

    BufferedImage generateImage();
}
