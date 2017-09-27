package layers.web.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import layers.web.vaadin.component.layout.*;
import layers.web.vaadin.component.visual.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;


@SpringUI(path = "/*")
@Scope("prototype")
@Theme("mytheme")
@Title("Image Generator")
public class ImageGeneratorUI extends UI {

    @Autowired
    private HeaderLayout headerLayout;

    @Autowired
    private RadioButtonPatternsGroup patternsGroup;

    @Autowired
    private SliderLayout sliderLayout;

    @Autowired
    private ButtonsLayout buttonsLayout;

    @Autowired
    private ImagesLayout imagesLayout;

    @Autowired
    private FooterLayout footerLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.addComponents(headerLayout, patternsGroup, sliderLayout, buttonsLayout, imagesLayout, footerLayout);

        verticalLayout.setComponentAlignment(patternsGroup, Alignment.TOP_CENTER);

        setContent(verticalLayout);
    }
}