package utility.core;

import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.BigDecimal.valueOf;

public class InformationalColor extends Color {

    InformationalColor(int red, int green, int blue) {
        super(red, green, blue);
    }

    public boolean almostIdentical(Color otherColor) {
        return almostIdentical(this.getRed(), otherColor.getRed()) &&
               almostIdentical(this.getGreen(), otherColor.getGreen()) &&
               almostIdentical(this.getBlue(), otherColor.getBlue());
    }

    private boolean almostIdentical(int firstColor, int secondColor) {
        BigDecimal delta        = valueOf(255.0).divide(valueOf(3.0), MathContext.DECIMAL32);
        BigDecimal firstValue   = valueOf(firstColor); // have to add 0.01 to avoid division by zero
        BigDecimal secondValue  = valueOf(secondColor);// have to add 0.01 to avoid division by zero
        BigDecimal smallerValue = (firstValue.compareTo(secondValue) < 0) ? firstValue : secondValue;
        BigDecimal greaterValue = (firstValue.compareTo(secondValue) < 0) ? secondValue : firstValue;

        return smallerValue.add(delta, MathContext.DECIMAL32).compareTo(greaterValue) > 0;
    }

}
