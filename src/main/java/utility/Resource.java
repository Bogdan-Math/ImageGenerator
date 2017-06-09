package utility;

import basic.ColorInfo;
import basic.ObjectTypeConverter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Resource {

    private final ColorInfo colorInfo = new ColorInfo();

    public Map<Color, BufferedImage> getPatternsIn(String path) {

        ObjectTypeConverter converter = new ObjectTypeConverter();

        return Optional.ofNullable(readFiles(path))
                .orElseThrow(() -> new RuntimeException("Directory \'" + path + "\': is not exist or empty."))
                .stream()
                .collect(Collectors
                        .toMap(
                                file -> colorInfo.averagedColor(converter.bufferedImage(file)), // put Color         like KEY in map
                                converter::bufferedImage,                                       // put BufferedImage like VALUE in map
                                (img_color_1, img_color_2) -> {
                                    System.out.println("Two same average color: ");
                                    System.out.println(img_color_1);
                                    System.out.println(img_color_2);

                                    return img_color_1;
                                }
                        )
                );
    }

    public List<File> readFiles(String path) {
        return Arrays.stream(readFile(path).listFiles())
                     .filter(File::isFile)
                     .collect(Collectors.toList());
    }

    public File readFile(String resourceName) {
        return new File(getClass().getClassLoader()
                                            .getResource("")
                                            .getPath() + resourceName);
    }

}