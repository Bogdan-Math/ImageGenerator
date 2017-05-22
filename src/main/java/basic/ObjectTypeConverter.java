package basic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ObjectTypeConverter {

    public void copyImage(File fromFile, File toFile, String outputFormat) {
        try {
            ImageIO.write(bufferedImageFromFile(fromFile), outputFormat, toFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public BufferedImage bufferedImageFromFile(File fromFile) {
        try {
            return ImageIO.read(fromFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public InputStream inputStreamFromBufferedImage(BufferedImage bufferedImage, String format) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, format, baos);
            baos.flush();
            byte[] imageInBytes = baos.toByteArray();
            baos.close();
            return new ByteArrayInputStream(imageInBytes);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public BufferedImage bufferedImageFromInputStream(InputStream inputStream) {
        try {
            return ImageIO.read(inputStream);
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }



}
