package core;

import domain.InformationalColor;
import domain.InformationalImage;

import java.util.Map;

public class BasicSettings implements Settings {

    private InformationalImage incomeImage;
    private Map<InformationalColor, InformationalImage> patterns;
    private Integer expectedColumnsCount;
    private String incomeImageName;

    @Override
    public InformationalImage getIncomeImage() {
        return incomeImage;
    }

    @Override
    public void setIncomeImage(InformationalImage incomeImage) {
        this.incomeImage = incomeImage;
    }

    @Override
    public Map<InformationalColor, InformationalImage> getPatterns() {
        return patterns;
    }

    @Override
    public void setPatterns(Map<InformationalColor, InformationalImage> patterns) {
        this.patterns = patterns;
    }

    @Override
    public Integer getExpectedColumnsCount() {
        return expectedColumnsCount;
    }

    @Override
    public void setExpectedColumnsCount(Integer expectedColumnsCount) {
        this.expectedColumnsCount = expectedColumnsCount;
    }

    @Override
    public int getImageWidth() {
        return getIncomeImage().getWidth();
    }

    @Override
    public int getImageHeight() {
        return getIncomeImage().getHeight();
    }

    @Override
    public InformationalImage getSubImage(int x, int y, int width, int height) {
        return getIncomeImage().getSubImage(x, y, width, height);
    }

    @Override
    public String getIncomeImageName() {
        return incomeImageName;
    }

    @Override
    public void setIncomeImageName(String incomeImageName) {
        this.incomeImageName = incomeImageName;
    }

}
