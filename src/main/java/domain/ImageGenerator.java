package domain;

import utility.pattern.InformationalImage;

public interface ImageGenerator {

    void setSettings(Settings settings);

    InformationalImage generateImage();
}
