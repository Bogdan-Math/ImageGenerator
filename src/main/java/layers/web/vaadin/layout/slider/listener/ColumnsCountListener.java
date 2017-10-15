package layers.web.vaadin.layout.slider.listener;

import static domain.Settings.MAX_EXPECTED_COLUMNS_COUNT;
import static domain.Settings.MIN_EXPECTED_COLUMNS_COUNT;

public interface ColumnsCountListener {

    Integer HINT_EXPECTED_COLUMNS_COUNT = 8;

    String EMPTY_COLUMNS_COUNT_MESSAGE = "Input values should " +
                                          "<b>NOT</b> " +
                                          "be empty!";

    String NUMERIC_COLUMNS_COUNT_MESSAGE = "Input value should be a " +
                                            "<b>NUMERIC</b>" +
                                            "!";

    String HINT_COLUMNS_COUNT_MESSAGE = "No, no, no... Trust me, " +
                                         "<b>" + HINT_EXPECTED_COLUMNS_COUNT + "</b>" +
                                         " - it's " +
                                         "<b>OK</b>" +
                                         "!";

    String BOUNDS_COLUMNS_COUNT_MESSAGE = "Input value should be from " +
                                           "<b>" + MIN_EXPECTED_COLUMNS_COUNT + "</b>" +
                                           " to " +
                                           "<b>" + MAX_EXPECTED_COLUMNS_COUNT + "</b>";

    void changeValueTo(Integer newValue);
}