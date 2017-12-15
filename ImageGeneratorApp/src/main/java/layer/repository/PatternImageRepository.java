package layer.repository;

import model.PatternImage;

import java.util.stream.Stream;

public interface PatternImageRepository {

    Stream<PatternImage> getCommons();
    Stream<PatternImage> getFlags();
    Stream<PatternImage> getPlains();
}
