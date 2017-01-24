package basic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Image {

    public static final int RED_IN_INT = (255 << 16);
    public static final int GREEN_IN_INT = (255 << 8);
    public static final int BLUE_IN_INT = (255);

    private BufferedImage image;

    public Image workOn(BufferedImage image) {
        this.image = image;
        return this;
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    public Image getCopy() {
        BufferedImage b = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = b.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return new Image().workOn(b);
    }

    public BufferedImage getSubImage(int x, int y, int width, int height) throws IOException {
        return image.getSubimage(x, y, width, height);
    }

    public List<List<BufferedImage>> likeMatrix(int columns, int rows) {
        int width = image.getWidth();
        int height = image.getHeight();
        int squareWidth = width / columns;
        int squareHeight = height / rows;

        List<List<BufferedImage>> matrix = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            List<BufferedImage> matrixRow = new ArrayList<>();
            for (int j = 0; j < rows; j++) {
                matrixRow.add(image.getSubimage(i * squareWidth, j * squareHeight, squareWidth, squareHeight));
            }
            matrix.add(matrixRow);
        }

        return matrix;
    }

    public int averageRGB() {
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

        return (avrR<<16) | (avrG<<8) | avrB;
    }

    public List<List<Integer>> averageRGBMatrix(int columns, int rows) {
        return likeMatrix(columns, rows).stream()
                .map(row -> row.stream()
                        .map(img -> new Image().workOn(img).averageRGB())
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public BufferedImage createRedImg(int width, int height) {
        return createOneColorImg(width, height, new Color(255, 0, 0));
    }

    public BufferedImage createGreenImg(int width, int height) {
        return createOneColorImg(width, height, new Color(0, 255, 0));
    }

    public BufferedImage createBlueImg(int width, int height) {
        return createOneColorImg(width, height, new Color(0, 0, 255));
    }

    private BufferedImage createOneColorImg(int width, int height, Color color) {
        BufferedImage redImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < redImg.getWidth(); i++) {
            for (int j = 0; j < redImg.getHeight(); j++) {
                redImg.setRGB(i, j, (color.getRed() << 16) | (color.getGreen() << 8) | color.getBlue());
            }
        }
        return redImg;
    }
}