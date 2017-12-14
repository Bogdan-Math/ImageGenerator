package layer.repository;

import model.PatternImage;

import java.util.List;

public interface PatternImageRepository {

    List<PatternImage> getCommons();
    List<PatternImage> getFlags();
    List<PatternImage> getPlains();
}
