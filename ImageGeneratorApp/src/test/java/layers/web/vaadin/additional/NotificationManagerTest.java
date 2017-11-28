package layers.web.vaadin.additional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class NotificationManagerTest {

    @Autowired
    private NotificationManager notificationManager;

    @Test
    public void showAsString() throws Exception {
        //TODO: implement it
    }

    @Test
    public void showAsHtml() throws Exception {
        //TODO: implement it
    }

    @Test
    public void addAndBuild() throws Exception {
        String message = notificationManager.add("notification 1")
                                            .add("notification 2")
                                            .add("notification 3")
                                            .build();

        assertThat(message, is("- notification 1\n- notification 2\n- notification 3"));
    }
}