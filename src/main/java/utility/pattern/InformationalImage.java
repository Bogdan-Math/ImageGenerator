package utility.pattern;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

public class InformationalImage {
    public static final int TYPE_INT_RGB = BufferedImage.TYPE_INT_RGB;

    private BufferedImage bufferedImage;

    public InformationalImage(BufferedImage bufferedImage) {
        setBufferedImage(bufferedImage);
    }

    public InformationalImage(int width, int height, int typeIntRgb) {
        setBufferedImage(new BufferedImage(width, height, typeIntRgb));
    }

    public Color averagedColor() {

        int width  = getBufferedImage().getWidth();
        int height = getBufferedImage().getHeight();
        int pixels = width * height;

        final int[] sumR = {0};
        final int[] sumG = {0};
        final int[] sumB = {0};

        final int[] avrR = {0};
        final int[] avrG = {0};
        final int[] avrB = {0};

        IntStream.range(0, width).forEach( (int i) -> {

            IntStream.range(0, height).forEach((int j) -> {

                int rgb = getBufferedImage().getRGB(i, j);

                int r = (rgb & 0x00ff0000) >> 16;
                int g = (rgb & 0x0000ff00) >> 8;
                int b =  rgb & 0x000000ff;

                sumR[0] += r;
                sumG[0] += g;
                sumB[0] += b;

            });

            avrR[0] = sumR[0] / pixels;
            avrG[0] = sumG[0] / pixels;
            avrB[0] = sumB[0] / pixels;
        });

        return new Color(avrR[0], avrG[0], avrB[0]);
    }

    public InformationalImage getSubImage(int x, int y, int width, int height) {
        return new InformationalImage(getBufferedImage().getSubimage(x, y, width, height));
    }

    public int getWidth() {
        return getBufferedImage().getWidth();
    }

    public int getHeight() {
        return getBufferedImage().getHeight();
    }

    public Graphics2D createGraphics() {
        return getBufferedImage().createGraphics();
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    private void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
