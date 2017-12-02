package layers.repository;

import domain.InformationalImage;

import java.util.List;

public interface GalleryRepository {

    InformationalImage get(String imageName);

    List<InformationalImage> getAll();
}
