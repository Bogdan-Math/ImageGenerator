package layers.repository;

import domain.InformationalImage;

public interface GalleryRepository {

    InformationalImage get(String imageName);
}
