package web.vaadin;

import basic.ObjectTypeConverter;
import com.vaadin.annotations.Theme;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;
import utility.FileReader;

import java.io.InputStream;

@Theme("mytheme")
public class MyUI extends UI {

    private ObjectTypeConverter typeConverter = new ObjectTypeConverter();
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout(new Label("VAADIN"));


        Image image = new Image("!!!", new StreamResource(createStreamResource(), "images/canonical.jpg"));
        layout.addComponent(image);
        setContent(layout);
    }

    private StreamResource.StreamSource createStreamResource() {
        return new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                FileReader fileReader = new FileReader();
                return typeConverter
                        .inputStreamFromBufferedImage(typeConverter
                                .bufferedImageFromFile(fileReader
                                        .getFileObject("images/canonical.jpg")), "jpg");
            }
        };
    }

}
