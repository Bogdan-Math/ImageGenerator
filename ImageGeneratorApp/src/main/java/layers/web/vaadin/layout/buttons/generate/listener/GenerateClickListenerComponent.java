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

    private static final int NEW_WIDTH  = 300;
    private static final int NEW_HEIGHT = 300;

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
            String generatedImageName         = String.join("_", "generated", timeNow, settings.getIncomeImageName());

            generatedImageView.setSource(new StreamResource(
                    generatedImage::asStream,
                    generatedImageName));

            downloader.setFileDownloadResource(generatedImageView.getSource());

            notificationManager.add("Your image was generated.")
                               .showAs(TRAY_NOTIFICATION);

            galleryImageService.save(new GalleryImage() {{
                setName(generatedImageName);
                setFullImage(generatedImage.asBytes());
                setThumbnailImage(generatedImage.resizeTo(NEW_WIDTH, NEW_HEIGHT)//TODO: move resize process to scheduler
                                                .asBytes());
            }});

        });
    }
}
