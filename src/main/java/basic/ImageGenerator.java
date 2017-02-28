package basic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ImageGenerator {

    private BufferedImage image;
    private Map<Color, BufferedImage> patterns;
    private Integer expectedColumnsNumber;

    public Color averageRGB() {
        int width = image.getWidth();
        int height = image.getHeight();
        int pixels = width * height;

        int sumR = 0;
        int sumG = 0;
        int sumB = 0;

        int avrR = 0;
        int avrG = 0;
        int avrB = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);

                int r = (rgb & 0x00ff0000) >> 16;
                int g = (rgb & 0x0000ff00) >> 8;
                int b =  rgb & 0x000000ff;

                sumR += r;
                sumG += g;
                sumB += b;
            }

            avrR = sumR / pixels;
            avrG = sumG / pixels;
            avrB = sumB / pixels;
        }

        return new Color(avrR, avrG, avrB);
    }

    public List<List<BufferedImage>> likeMatrix(int expectedColumns) {
                                                int expectedRows = 0;

        int width = image.getWidth();
        int height = image.getHeight();

        if (expectedColumns > width) {
            throw new ExpectedMatrixSizeException(String
                    .format("Number of expected columns (is %s) could not be more than image width (is %s).", expectedColumns, width));
        }

        int squareWidth = width / expectedColumns;
        int squareHeight = squareWidth * ImageSize.HEIGHT / ImageSize.WIDTH;

        squareHeight = (squareHeight != 0) ? squareHeight : 1;

        while (width - squareWidth * expectedColumns >= squareWidth ) {
            expectedColumns++;
        }

        while (height - squareHeight * expectedRows >= squareHeight ) {
            expectedRows++;
        }

        List<List<BufferedImage>> matrix = new ArrayList<>();
        for (int i = 0; i < expectedColumns; i++) {
            List<BufferedImage> matrixRow = new ArrayList<>();
            for (int j = 0; j < expectedRows; j++) {
                matrixRow.add(image.getSubimage(i * squareWidth, j * squareHeight, squareWidth, squareHeight));
            }
            matrix.add(matrixRow);
        }

        return matrix;
    }

    public List<List<Color>> averageRGBMatrix() {
        return likeMatrix(this.expectedColumnsNumber).stream()
                .map(row -> row.stream()
                        .map(img -> new ImageGenerator().setImage(img).averageRGB())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public BufferedImage generateImageFrom(List<List<BufferedImage>> imgMatrix) {
        int columns = imgMatrix.size();
        int rows = imgMatrix.get(0).size();

        BufferedImage imageWithMaxSize = findImageWithMaxSize(imgMatrix);
        int width = imageWithMaxSize.getWidth() * columns;
        int height = imageWithMaxSize.getHeight() * rows;

        int squareWidth = width / columns;
        int squareHeight = height / rows;

        BufferedImage averageImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {

                Graphics graphics = averageImg.getGraphics();
                graphics.drawImage(resize(imgMatrix.get(i).get(j), imageWithMaxSize.getWidth(), imageWithMaxSize.getHeight()),
                        i * squareWidth,
                        j * squareHeight,
                        null);
            }
        }

        return averageImg;
    }


    public BufferedImage createRedImg(int width, int height) {
        return createOneColorImg(width, height, Color.red);
    }

    public BufferedImage createGreenImg(int width, int height) {
        return createOneColorImg(width, height, Color.green);
    }

    public BufferedImage createBlueImg(int width, int height) {
        return createOneColorImg(width, height, Color.blue);
    }

    private BufferedImage createOneColorImg(int width, int height, Color color) {
        BufferedImage redImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < redImg.getWidth(); i++) {
            for (int j = 0; j < redImg.getHeight(); j++) {
                redImg.setRGB(i, j, ( color.getRed()<<16 | color.getGreen()<<8 | color.getBlue() ));
            }
        }
        return redImg;
    }

    private BufferedImage resize(BufferedImage oldImage, int newWidth, int newHeight) {
        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImg.createGraphics();
        graphics2D.drawImage(oldImage, 0, 0, resizedImg.getWidth(), resizedImg.getHeight(), null);
        return resizedImg;
    }

    private BufferedImage findImageWithMaxSize(List<List<BufferedImage>> imgMatrix) {
        return imgMatrix.stream().flatMap(List::stream).max(Comparator.comparing(img -> img.getWidth() * img.getHeight())).orElse(null);
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

}