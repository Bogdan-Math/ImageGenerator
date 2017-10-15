package layers.web.vaadin.layout.buttons.generate.listener;

import com.vaadin.server.StreamResource;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Image;
import domain.ImageGenerator;
import domain.Settings;
import layers.web.vaadin.layout.buttons.download.listener.Downloader;
import layers.web.vaadin.additional.NotificationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utility.core.InformationalImage;

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
    private NotificationBuilder notificationBuilder;

    @Autowired
    private Downloader downloader;

    @Override
    public void buttonClick(ClickEvent event) {

        ofNullable(settings.getIncomeImage()).ifPresent(image -> {

            InformationalImage generatedImage = imageGenerator.generateImage();
            String timeNow                    = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));

            generatedImageView.setSource(new StreamResource(
                    generatedImage::asStream,
                    String.join("_", "generated", timeNow, settings.getImageFileName())));

            downloader.setFileDownloadResource(generatedImageView.getSource());

            notificationBuilder.add("Your image was generated.");
            notificationBuilder.showAsString(TRAY_NOTIFICATION);
        });

    }
}
