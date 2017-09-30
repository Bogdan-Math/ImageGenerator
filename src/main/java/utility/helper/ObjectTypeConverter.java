package utility.helper;

import org.springframework.stereotype.Component;
import utility.pattern.InformationalImage;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import static javax.imageio.ImageIO.read;

@Component
public class ObjectTypeConverter {

    //TODO: check method usages
    public InputStream inputStream(byte[] bytes) {
        return new ByteArrayInputStream(bytes);
    }

    //TODO: try move it to InformationalImage
    public InputStream inputStream(InformationalImage informationalImage) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(informationalImage, "jpg", stream);
            stream.flush();
            byte[] imageInBytes = stream.toByteArray();
            stream.close();
            return new ByteArrayInputStream(imageInBytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public InformationalImage informationalImage(byte[] bytes) {
        try {
            return new InformationalImage(read(new ByteArrayInputStream(bytes)));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

    public InformationalImage informationalImage(File fromFile) {
        try {
            return new InformationalImage(read(fromFile));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }
    }

}
