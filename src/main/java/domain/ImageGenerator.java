package domain;

import utility.core.InformationalImage;

public interface ImageGenerator {

    void setSettings(Settings settings);

    InformationalImage generateImage();
}
