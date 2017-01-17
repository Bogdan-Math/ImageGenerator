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

    public BufferedImage getCopy(){
        BufferedImage b = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = b.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return b;
    }

    public BufferedImage getSubImage(int x, int y, int width, int height) throws IOException {
        return image.getSubimage(x, y, width, height);
    }

}
