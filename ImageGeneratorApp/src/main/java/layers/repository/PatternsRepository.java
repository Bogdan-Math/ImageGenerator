package layers.repository;

import domain.InformationalImage;

import java.awt.*;
import java.util.Map;

public interface PatternsRepository {

    Map<Color, InformationalImage> getCommons();
    Map<Color, InformationalImage> getFlags();
    Map<Color, InformationalImage> getPlains();
}
