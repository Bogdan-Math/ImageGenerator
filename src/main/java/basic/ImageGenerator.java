package basic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageGenerator {

    public void copyImage(File fromFile, File toFile, String outputFormat) {
        try {
            ImageIO.write(fileToBufferedImage(fromFile), outputFormat, toFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage fileToBufferedImage(File fromFile) throws IOException {
        return ImageIO.read(fromFile);
    }

    public byte[] bufferedImageToByteArray(BufferedImage bufferedImage, String format) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, format, baos);
            baos.flush();
            byte[] imageInBytes = baos.toByteArray();
            baos.close();
            return imageInBytes;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage byteArrayToBufferedImage(byte[] imageInBytes) {
        try {
            return ImageIO.read(new ByteArrayInputStream(imageInBytes));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
