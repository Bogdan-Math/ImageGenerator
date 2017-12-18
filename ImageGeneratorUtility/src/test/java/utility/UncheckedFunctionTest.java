package utility;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UncheckedFunctionTest {

    private UncheckedFunction<String, Integer> uncheckedFunction;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        this.uncheckedFunction = Integer::valueOf;
    }

    @Test
    public void apply() {
        Integer zero  = 0;
        Integer value = uncheckedFunction.apply("0");

        assertThat(value, equalTo(zero));
    }

    @Test
    public void uncheckedApply() {
        String wrongInput = "WRONG_INPUT";
        thrown.expect(UncheckedFunction.UncheckedFunctionException.class);
        thrown.expectMessage(wrongInput);

        uncheckedFunction.apply(wrongInput);
    }
}
