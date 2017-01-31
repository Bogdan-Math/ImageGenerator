package basic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ObjectTypeConverter {

    public void copyImage(File fromFile, File toFile, String outputFormat) {
        try {
            ImageIO.write(bufferedImageFromFile(fromFile), outputFormat, toFile);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something happened !!!", e);
        }
    }

    public BufferedImage bufferedImageFromFile(File fromFile) {
        try {
            return ImageIO.read(fromFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something happened !!!", e);
        }
    }

    public byte[] byteArrayFromBufferedImage(BufferedImage bufferedImage, String format) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, format, baos);
            baos.flush();
            byte[] imageInBytes = baos.toByteArray();
            baos.close();
            return imageInBytes;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something happened !!!", e);
        }
    }

    public BufferedImage bufferedImageFromByteArray(byte[] imageInBytes) {
        try {
            return ImageIO.read(new ByteArrayInputStream(imageInBytes));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something happened !!!", e);
        }
    }

}
