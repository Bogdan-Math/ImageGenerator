package layers.repository;

import domain.InformationalColor;
import domain.InformationalImage;

import java.util.Map;

public interface PatternsRepository {

    Map<InformationalColor, InformationalImage> getCommons();
    Map<InformationalColor, InformationalImage> getFlags();
    Map<InformationalColor, InformationalImage> getPlains();
}
