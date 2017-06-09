package layers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utility.exceptions.MatrixSizeException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ImageGenerator {

    /**
     * The values of ImageSize should be as close as possible to patterns average size, if they different.
     * The best way if all patterns have same width and height. Then set it in in WIDTH and HEIGHT and you are C00L :).
     */
    private static final int WIDTH = 40;
    private static final int HEIGHT = 20;

    @Autowired
    private ColorInfo colorInfo;

    private BufferedImage image;
    private Map<Color, BufferedImage> patterns;
    private Integer expectedColumnsNumber;

    //TODO: add descriptive comments to this method
    public List<List<BufferedImage>> asMatrix(int expectedColumns) {
                                                int expectedRows = 0;

        int width  = image.getWidth();
        int height = image.getHeight();

        if (expectedColumns > width) {
            throw new MatrixSizeException(String
                    .format("Number of expected columns (is %s) could not be more than image width (is %s).", expectedColumns, width));
        }

        int realColumnsNumber = expectedColumns;
        int realRowsNumber    = expectedRows;

        int squareWidth  = width / realColumnsNumber;
        int squareHeight = squareWidth * HEIGHT / WIDTH;

        squareHeight = (squareHeight != 0) ? squareHeight : 1;

        while (width - squareWidth * realColumnsNumber >= squareWidth ) {
            realColumnsNumber++;
        }

        while (height - squareHeight * realRowsNumber >= squareHeight ) {
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

    public List<List<Color>> averagedColorsMatrix() {
        return asMatrix(expectedColumnsNumber).stream()
                .map(row -> row.stream()
                        .map(colorInfo::averagedColor)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public List<List<BufferedImage>> resultMatrix() {

        List<List<Color>> matrix         = averagedColorsMatrix();
        Map<Color, BufferedImage> map    = getPatterns();
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

    public ImageGenerator setImage(BufferedImage image) {
        this.image = image;
        return this;
    }

    public ImageGenerator setPatterns(Map<Color, BufferedImage> patterns) {
        this.patterns = patterns;
        return this;
    }

    public ImageGenerator setExpectedColumnsNumber(Integer expectedColumnsNumber) {
        this.expectedColumnsNumber = expectedColumnsNumber;
        return this;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Map<Color, BufferedImage> getPatterns() {
        return patterns;
    }

    public Integer getExpectedColumnsNumber() {
        return expectedColumnsNumber;
    }

    //TODO: delete this method, after add Spring to tests
    public ImageGenerator setColorInfo(ColorInfo colorInfo) {
        this.colorInfo = colorInfo;
        return this;
    }
}