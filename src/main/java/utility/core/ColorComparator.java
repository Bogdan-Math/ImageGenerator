package utility.core;

import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.BigDecimal.valueOf;

//TODO: add test coverage
//TODO: try extend java.awt.Color class by this methods
public class ColorComparator {

    public static boolean almostIdentical(Color colorOne, Color colorTwo) {
        return almostIdentical(colorOne.getRed(), colorTwo.getRed()) &&
                almostIdentical(colorOne.getGreen(), colorTwo.getGreen()) &&
                almostIdentical(colorOne.getBlue(), colorTwo.getBlue());
    }

    private static boolean almostIdentical(int firstColor, int secondColor) {
        BigDecimal delta        = valueOf(255.0).divide(valueOf(3.0), MathContext.DECIMAL32);
        BigDecimal firstValue   = valueOf(firstColor); // have to add 0.01 to avoid division by zero
        BigDecimal secondValue  = valueOf(secondColor);// have to add 0.01 to avoid division by zero
        BigDecimal smallerValue = (firstValue.compareTo(secondValue) < 0) ? firstValue : secondValue;
        BigDecimal greaterValue = (firstValue.compareTo(secondValue) < 0) ? secondValue : firstValue;

        return smallerValue.add(delta, MathContext.DECIMAL32).compareTo(greaterValue) > 0;
    }

}
