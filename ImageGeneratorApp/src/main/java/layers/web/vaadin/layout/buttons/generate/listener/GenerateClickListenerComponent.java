package layers.web.vaadin.layout.buttons.generate.listener;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Image;
import core.ImageGenerator;
import core.Settings;
import domain.InformationalImage;
import layers.service.GalleryImageService;
import layers.web.vaadin.additional.NotificationManager;
import layers.web.vaadin.layout.buttons.download.listener.Downloader;
import model.GalleryImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION;
import static java.util.Optional.ofNullable;

@Component
@Scope("session")
public class GenerateClickListenerComponent implements GenerateClickListener {

    @Autowired
    private ImageGenerator imageGenerator;

    @Autowired
    private Settings settings;

    @Autowired
    @Qualifier(value = "generatedImageView")
    private Image generatedImageView;

    @Autowired
    private NotificationManager notificationManager;

    @Autowired
    private Downloader downloader;

    @Autowired
    private GalleryImageService galleryImageService;

    @Override
    public void buttonClick(ClickEvent event) {

        ofNullable(settings.getIncomeImage()).ifPresent(image -> {

            InformationalImage generatedImage = imageGenerator.generateImage();
            String timeNow                    = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

            generatedImageView.setSource(new StreamResource(
                    generatedImage::asStream,
                    String.join("_", "generated", timeNow, settings.getImageFileName())));

            downloader.setFileDownloadResource(generatedImageView.getSource());

            notificationManager.add("Your image was generated.")
                               .showAs(TRAY_NOTIFICATION);

            new Thread(() -> galleryImageService.save(new GalleryImage() {{
                setName(settings.getImageFileName());//TODO: add correct generated name
                setBytes(generatedImage.resizeTo(250, 250).asBytes());//TODO: move variables to constants
            }})).start();
        });
    }
}
