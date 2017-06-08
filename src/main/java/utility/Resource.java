package utility;

import basic.AveragedColor;
import basic.ObjectTypeConverter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Resource {

    private final AveragedColor averagedColor = new AveragedColor();

    public Map<Color, BufferedImage> getPatternsFrom(String path) {

        ObjectTypeConverter objectTypeConverter = new ObjectTypeConverter();

        return Arrays.stream(Optional.ofNullable(readFile(path).listFiles())
                .orElseThrow(() -> new RuntimeException("Directory \'" + path + "\': is not exist or empty.")))
                .filter(File::isFile)
                .collect(Collectors.toMap(
                                file -> averagedColor.averagedColor(objectTypeConverter.bufferedImage(file)),
                                objectTypeConverter::bufferedImage,
                                (img_color_1, img_color_2) -> {
                                    System.out.println("Two same average color: ");
                                    System.out.println(img_color_1);
                                    System.out.println(img_color_2);

                                    return img_color_1;
                                }
                        )
                );
    }

    public File readFile(String resourceName) {
        return new File(getClass().getClassLoader().getResource("").getPath() + resourceName);
    }

}