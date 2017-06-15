package utility.helpers;

import layers.service.ColorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class PatternManager {

    @Autowired
    private ObjectTypeConverter converter;

    @Autowired
    private ColorInfo colorInfo;

    public Map<Color, BufferedImage> patternsMap(List<File> files) {
        return files.stream()
                .collect(Collectors
                        .toMap(
                                this::averagedColor,     // put Color         like KEY in map
                                converter::bufferedImage,// put BufferedImage like VALUE in map

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
        return colorInfo.averagedColor(converter.bufferedImage(file));
    }
}
