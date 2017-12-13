package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class AbstractImage {

    private String name;
    private byte[] fullImage;
    private byte[] thumbnailImage;
}
