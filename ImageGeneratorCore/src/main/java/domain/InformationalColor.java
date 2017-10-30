package domain;

import java.awt.*;
import java.math.BigDecimal;
import java.math.MathContext;

import static java.math.BigDecimal.valueOf;

public class InformationalColor extends Color {

    public static final InformationalColor WHITE = new InformationalColor(Color.WHITE);
    public static final InformationalColor GRAY  = new InformationalColor(Color.GRAY);
    public static final InformationalColor BLACK = new InformationalColor(Color.BLACK);
    public static final InformationalColor RED   = new InformationalColor(Color.RED);
    public static final InformationalColor GREEN = new InformationalColor(Color.GREEN);
    public static final InformationalColor BLUE  = new InformationalColor(Color.BLUE);

    InformationalColor(int red, int green, int blue) {
        super(red, green, blue);
    }

    private InformationalColor(Color color) {
        this(color.getRed(), color.getGreen(), color.getBlue());
    }

    public boolean almostEqualTo(InformationalColor otherColor) {
        return almostEquals(this.getRed(), otherColor.getRed()) &&
               almostEquals(this.getGreen(), otherColor.getGreen()) &&
               almostEquals(this.getBlue(), otherColor.getBlue());
    }

    private boolean almostEquals(int firstColor, int secondColor) {

        //delta: allow about 33% deviation of each pixel part (R,G,B)
        BigDecimal delta        = valueOf(255.0).divide(valueOf(3.0), MathContext.DECIMAL32);
        BigDecimal firstValue   = valueOf(firstColor);
        BigDecimal secondValue  = valueOf(secondColor);
        BigDecimal smallerValue = (firstValue.compareTo(secondValue) < 0) ? firstValue : secondValue;
        BigDecimal greaterValue = (firstValue.compareTo(secondValue) < 0) ? secondValue : firstValue;

        return smallerValue.add(delta, MathContext.DECIMAL32).compareTo(greaterValue) > 0;
    }

}
