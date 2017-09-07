package layers.repository;

import java.awt.image.BufferedImage;
import java.util.List;

public interface PatternsRepository {

    List<BufferedImage> getCommons();
    List<BufferedImage> getFlags();
    List<BufferedImage> getPlains();
}
