package domain;

import core.InformationalImage;

public interface ImageGenerator {

    void setSettings(Settings settings);

    InformationalImage generateImage();
}
