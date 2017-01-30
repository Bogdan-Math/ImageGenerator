package basic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ObjectTypeConverter {

    public void copyImage(File fromFile, File toFile, String outputFormat) throws IOException {
        ImageIO.write(bufferedImageFromFile(fromFile), outputFormat, toFile);
    }

    public BufferedImage bufferedImageFromFile(File fromFile) throws IOException {
        return ImageIO.read(fromFile);
    }

    public byte[] byteArrayFromBufferedImage(BufferedImage bufferedImage, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, baos);
        baos.flush();
        byte[] imageInBytes = baos.toByteArray();
        baos.close();
        return imageInBytes;
    }

    public BufferedImage bufferedImageFromByteArray(byte[] imageInBytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(imageInBytes));
    }

}
