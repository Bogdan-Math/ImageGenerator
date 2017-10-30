package core;

import domain.InformationalImage;

public interface ImageGenerator {

    void setSettings(Settings settings);

    InformationalImage generateImage();
}
