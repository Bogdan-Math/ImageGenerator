package layers.repository;

import domain.PatternType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import utility.helpers.ImageInformation;
import utility.helpers.ObjectTypeConverter;
import utility.helpers.ResourceReader;

import javax.annotation.Resource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static domain.PatternType.*;

@Repository
@Scope("singleton")
public class BasicPatternsRepository implements PatternsRepository {

    @Autowired
    private ResourceReader resourceReader;

    @Autowired
    private ObjectTypeConverter converter;

    @Autowired
    private ImageInformation imageInformation;

    @Resource(name = "patternsLocation")
    private Map<PatternType, String> patternsLocation;

    @Override
    public Map<Color, BufferedImage> getCommons() {
        return initialize(COMMONS);
    }

    @Override
    public Map<Color, BufferedImage> getFlags() {
        return initialize(FLAGS);
    }

    @Override
    public Map<Color, BufferedImage> getPlains() {
        return initialize(PLAINS);
    }

    private Map<Color, BufferedImage> initialize(PatternType patternType) {
        return patternsMap(resourceReader.readFiles(patternsLocation.get(patternType)));
    }

    private Map<Color, BufferedImage> patternsMap(List<File> files) {
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

    private Color averagedColor(File file) {
        return imageInformation.averagedColor(converter.bufferedImage(file));
    }

    //TODO: delete this method, after add Spring to tests
    public void setResourceReader(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
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