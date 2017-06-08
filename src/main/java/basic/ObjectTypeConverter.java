package basic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

public class ObjectTypeConverter {

    public InputStream inputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    public InputStream inputStream(BufferedImage bufferedImage) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            byte[] imageInBytes = baos.toByteArray();
            baos.close();
            return new ByteArrayInputStream(imageInBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public BufferedImage bufferedImage(byte[] bytes) {
        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public BufferedImage bufferedImage(File fromFile) {
        try {
            return ImageIO.read(fromFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

}
