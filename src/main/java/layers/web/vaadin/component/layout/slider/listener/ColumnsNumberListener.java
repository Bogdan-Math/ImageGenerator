package layers.web.vaadin.component.layout.slider.listener;

import static domain.Settings.MAX_NUMBER_OF_EXPECTED_COLUMNS;
import static domain.Settings.MIN_NUMBER_OF_EXPECTED_COLUMNS;

public interface ColumnsNumberListener {

    Integer HINT_NUMBER_OF_EXPECTED_COLUMNS = 8;

    String EMPTY_COLUMNS_NUMBER_MESSAGE = "Input values should " +
                                          "<b>NOT</b> " +
                                          "be empty!";

    String NUMERIC_COLUMNS_NUMBER_MESSAGE = "Input value should be a " +
                                            "<b>NUMERIC</b>" +
                                            "!";

    String HINT_COLUMNS_NUMBER_MESSAGE = "No, no, no... Trust me, " +
                                         "<b>" + HINT_NUMBER_OF_EXPECTED_COLUMNS + "</b>" +
                                         " - it's " +
                                         "<b>OK</b>" +
                                         "!";

    String BOUNDS_COLUMNS_NUMBER_MESSAGE = "Input value should be from " +
                                           "<b>" + MIN_NUMBER_OF_EXPECTED_COLUMNS + "</b>" +
                                           " to " +
                                           "<b>" + MAX_NUMBER_OF_EXPECTED_COLUMNS + "</b>";

    void changeValueTo(Integer newValue);
}