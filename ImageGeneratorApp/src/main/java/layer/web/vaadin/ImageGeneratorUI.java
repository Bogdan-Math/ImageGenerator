package layer.web.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import layer.web.vaadin.layout.Tabs;
import layer.web.vaadin.layout.footer.FooterLayout;
import layer.web.vaadin.layout.header.HeaderLayout;
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
    private Tabs tabs;

    @Autowired
    private FooterLayout footerLayout;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout verticalLayout = new VerticalLayout();

        verticalLayout.addComponents(headerLayout, tabs, footerLayout);

        setContent(verticalLayout);
    }
}