package utility.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PatternManager {

    @Autowired
    private ObjectTypeConverter converter;

    @Autowired
    private ImageInformation imageInformation;

    public Map<Color, BufferedImage> patternsMap(List<File> files) {
        return files.stream()
                .collect(Collectors
                        .toMap(
                                this::averagedColor,     // put Color         as KEY   in map
                                converter::bufferedImage,// put BufferedImage as VALUE in map

                                (img_color_1, img_color_2) -> {
                                    System.out.println("Two same average color: ");
                                    System.out.println(img_color_1);
                                    System.out.println(img_color_2);

                                    return img_color_1;
                                }
                        )
                );
    }

    public Color averagedColor(File file) {
        return imageInformation.averagedColor(converter.bufferedImage(file));
    }

    //TODO: delete this method, after add Spring to tests
    public void setConverter(ObjectTypeConverter converter) {
        this.converter = converter;
    }

    //TODO: delete this method, after add Spring to tests
    public void setImageInformation(ImageInformation imageInformation) {
        this.imageInformation = imageInformation;
    }
}
