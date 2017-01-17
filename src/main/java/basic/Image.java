package basic;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image {

    private BufferedImage image;

    public Image(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getOriginalImage() {
        return image;
    }

    public BufferedImage getCopy() {
        BufferedImage b = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = b.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return b;
    }

    public BufferedImage getSubImage(int x, int y, int width, int height) throws IOException {
        return image.getSubimage(x, y, width, height);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;
        BufferedImage comparedImage = image.getOriginalImage();
        BufferedImage thisImage = this.getOriginalImage();

        if (thisImage.getWidth() == comparedImage.getWidth() && thisImage.getHeight() == comparedImage.getHeight()) {
            int width = thisImage.getWidth();
            int height = thisImage.getHeight();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (thisImage.getRGB(x, y) != comparedImage.getRGB(x, y)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return image != null ? image.hashCode() : 0;
    }
}
