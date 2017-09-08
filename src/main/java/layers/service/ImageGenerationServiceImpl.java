package layers.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import utility.exceptions.MatrixSizeException;
import utility.helpers.ImageInformation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Scope("session")
public class ImageGenerationServiceImpl implements ImageGenerationService {

    /**
     * The values of ImageSize should be as close as possible to patterns average size, if they different.
     * The best way if all patterns have same width and height. Then set it in in WIDTH and HEIGHT and you are C00L :).
     */
    private static final int PATTERN_WIDTH = 14;//40;
    private static final int PATTERN_HEIGHT = 14;//20;

    private BufferedImage image;
    private Map<Color, BufferedImage> patterns;
    private Integer expectedColumnsNumber;

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void setPatterns(Map<Color, BufferedImage> patterns) {
        this.patterns = patterns;
    }

    @Override
    public void setExpectedColumnsNumber(Integer expectedColumnsNumber) {
        this.expectedColumnsNumber = expectedColumnsNumber;
    }

    @Override
    public List<List<BufferedImage>> asMatrix() {

        int expectedColumns = expectedColumnsNumber;
        int expectedRows    = 0;

        int width  = image.getWidth();
        int height = image.getHeight();

        if (expectedColumns > width) {
            throw new MatrixSizeException(String
                    .format("Number of expected columns (is %s) could not be more than image width (is %s).", expectedColumns, width));
        }

        int realColumnsNumber = expectedColumns;
        int realRowsNumber    = expectedRows;

        int squareWidth  = width / realColumnsNumber;
        int squareHeight = squareWidth * PATTERN_HEIGHT / PATTERN_WIDTH;

        squareHeight = (squareHeight != 0) ? squareHeight : 1;

        while (width - squareWidth * realColumnsNumber >= squareWidth) {
            realColumnsNumber++;
        }

        while (height - squareHeight * realRowsNumber >= squareHeight) {
            realRowsNumber++;
        }

        List<List<BufferedImage>> matrix = new ArrayList<>();
        for (int i = 0; i < realColumnsNumber; i++) {

            List<BufferedImage> matrixRow = new ArrayList<>();
            for (int j = 0; j < realRowsNumber; j++) {
                matrixRow.add(image.getSubimage(i * squareWidth, j * squareHeight, squareWidth, squareHeight));
            }

            matrix.add(matrixRow);
        }

        return matrix;
    }

    @Override
    public List<List<Color>> averagedColorsMatrix() {
        return asMatrix().stream()
                .map(row -> row.stream()
                        .map(image -> new ImageInformation().averagedColor(image))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    @Override
    public List<List<BufferedImage>> resultMatrix() {

        List<List<Color>> matrix         = averagedColorsMatrix();
        Map<Color, BufferedImage> map    = patterns;
        List<List<BufferedImage>> result = new ArrayList<>();

        for (List<Color> colors : matrix) {
            List<BufferedImage> resultRows = new ArrayList<>();

            for (Color color : colors) {
                BufferedImage minImg = null;
                int minColor         = Integer.MAX_VALUE;

                for (Color pColor : map.keySet()) {
                    int dColor = Math.abs(color.getRed()   - pColor.getRed())   +
                                 Math.abs(color.getGreen() - pColor.getGreen()) +
                                 Math.abs(color.getBlue()  - pColor.getBlue());
                    if (dColor < minColor) {
                        minColor = dColor;
                        minImg   = map.get(pColor);
                    }
                }
                resultRows.add(minImg);

            }
            result.add(resultRows);
        }
        return result;
    }

    @Override
    public BufferedImage generateImage() {
        List<List<BufferedImage>> resultMatrix = resultMatrix();

        int columns = resultMatrix.size();
        int rows = resultMatrix.get(0).size();

        BufferedImage imageWithMaxSize = findImageWithMaxSize(resultMatrix);
        int width  = imageWithMaxSize.getWidth() * columns;
        int height = imageWithMaxSize.getHeight() * rows;

        int squareWidth = width / columns;
        int squareHeight = height / rows;

        BufferedImage averageImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {

                Graphics graphics = averageImg.getGraphics();
                graphics.drawImage(resize(resultMatrix.get(i).get(j), imageWithMaxSize.getWidth(), imageWithMaxSize.getHeight()),
                        i * squareWidth,
                        j * squareHeight,
                        null);
            }
        }

        return averageImg;
    }

    private BufferedImage resize(BufferedImage oldImage, int newWidth, int newHeight) {
        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D    = resizedImg.createGraphics();

        graphics2D.drawImage(oldImage, 0, 0, resizedImg.getWidth(), resizedImg.getHeight(), null);

        return resizedImg;
    }

    private BufferedImage findImageWithMaxSize(List<List<BufferedImage>> imgMatrix) {
        return imgMatrix.stream()
                        .flatMap(List::stream)
                        .max(Comparator.comparing(img -> img.getWidth() * img.getHeight()))
                        .orElse(null);
    }
}