package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GalleryImage {

    private String name;
    private byte[] bytes;
}
