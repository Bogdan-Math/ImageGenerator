package basic;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Image {

    private BufferedImage image;

    public Image(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getBufferedImage() {
        return image;
    }

    public Image getCopy() {
        BufferedImage b = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = b.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return new Image(b);
    }

    public BufferedImage getSubImage(int x, int y, int width, int height) throws IOException {
        return image.getSubimage(x, y, width, height);
    }

    public List<List<BufferedImage>> likeMatrix(int rows, int columns) {
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

}
