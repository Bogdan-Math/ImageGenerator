package layer.repository;

import domain.InformationalImage;

import java.util.stream.Stream;

public interface PatternRepository {

    Stream<InformationalImage> getCommons();
    Stream<InformationalImage> getFlags();
    Stream<InformationalImage> getPlains();
}
