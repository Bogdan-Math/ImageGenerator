package layers.service;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorInfo {

    public Color averagedColor(BufferedImage image) {
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
}
