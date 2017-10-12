package layers.web.vaadin.component.layout.slider.listener;

import static domain.Settings.MAX_NUMBER_OF_EXPECTED_COLUMNS;
import static domain.Settings.MIN_NUMBER_OF_EXPECTED_COLUMNS;

public interface ColumnsNumberListener {

    Integer HINT_NUMBER_OF_EXPECTED_COLUMNS = 8;

    String EMPTY_COLUMNS_NUMBER_MESSAGE = "Input values should NOT be empty!";
    String NUMERIC_COLUMNS_NUMBER_MESSAGE = "Input value should be a NUMERIC!";
    String HINT_COLUMNS_NUMBER_MESSAGE = "No, no, no... Trust me, " + HINT_NUMBER_OF_EXPECTED_COLUMNS + " - it's OK!";
    String BOUNDS_COLUMNS_NUMBER_MESSAGE = "Input value should be from " + MIN_NUMBER_OF_EXPECTED_COLUMNS + " to " + MAX_NUMBER_OF_EXPECTED_COLUMNS;

    void changeValueTo(Integer newValue);
}