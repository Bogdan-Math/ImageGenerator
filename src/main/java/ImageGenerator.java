import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageGenerator {

    public void copy(String formatName, File fromFile, File toFile) {
        try {

            byte[] imageInByte;
            BufferedImage originalImage = ImageIO.read(fromFile);

            // convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, formatName, baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();

            // convert byte array back to BufferedImage
            InputStream in = new ByteArrayInputStream(imageInByte);
            BufferedImage bImageFromConvert = ImageIO.read(in);

            ImageIO.write(bImageFromConvert, formatName, toFile);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
