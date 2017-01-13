import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageGenerator {

    public void copy(String format, File fromFile, File toFile) throws IOException {
            ImageIO.write(fileToBufferedImage(fromFile), format, toFile);
    }

    private BufferedImage fileToBufferedImage(File fromFile) throws IOException {
        return ImageIO.read(fromFile);
    }

    private byte[] bufferedImageToByteArray(BufferedImage bufferedImage, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, baos);
        baos.flush();
        byte[] imageInBytes = baos.toByteArray();
        baos.close();
        return imageInBytes;
    }

    private BufferedImage byteArrayToBufferedImage(byte[] imageInBytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(imageInBytes));
    }
}
