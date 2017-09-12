package domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utility.exception.MatrixSizeException;
import utility.helper.ImageInformation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static domain.Settings.PATTERN_HEIGHT;
import static domain.Settings.PATTERN_WIDTH;

@Component
@Scope("session")
public class BasicImageGenerator implements ImageGenerator {

    @Autowired
    private Settings settings;

    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    private List<List<BufferedImage>> asMatrix() {
        //TODO: add descriptive comments to this method

        int expectedColumns = settings.getExpectedColumnsNumber();
        int expectedRows    = 0;

        int width  = settings.getImageWidth();
        int height = settings.getImageHeight();

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
                matrixRow.add(settings.getSubImage(i * squareWidth, j * squareHeight, squareWidth, squareHeight));
            }

            matrix.add(matrixRow);
        }

        return matrix;
    }

    private List<List<Color>> averagedColorsMatrix() {
        ImageInformation imageInformation = new ImageInformation();
        return asMatrix().stream()
                .map(row -> row.stream()
                        //TODO: clear new ImageInformation after ImageConstructor creation
                        .map(imageInformation::averagedColor)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<List<BufferedImage>> resultMatrix() {

        List<List<Color>> matrix         = averagedColorsMatrix();
        Map<Color, BufferedImage> map    = settings.getPatterns();
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