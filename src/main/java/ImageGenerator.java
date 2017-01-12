import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageGenerator {

    public void copy(String formatName, File from, File to) {
        try {

            byte[] imageInByte;
            BufferedImage originalImage = ImageIO.read(from);

            // convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, formatName, baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();

            // convert byte array back to BufferedImage
            InputStream in = new ByteArrayInputStream(imageInByte);
            BufferedImage bImageFromConvert = ImageIO.read(in);

            ImageIO.write(bImageFromConvert, formatName, to);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
