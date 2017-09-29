package domain;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utility.pattern.InformationalImage;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.Map;

@Component
@Scope("session")
public class BasicSettings implements Settings {

    private InformationalImage incomeImage;
    private Map<Color, InformationalImage> patterns;
    private Integer expectedColumnsNumber;
    private String imageFileName;

    @PostConstruct
    public void postConstruct() {
        setExpectedColumnsNumber(MAX_NUMBER_OF_EXPECTED_COLUMNS / 2);
    }

    @Override
    public InformationalImage getIncomeImage() {
        return incomeImage;
    }

    @Override
    public void setIncomeImage(InformationalImage incomeImage) {
        this.incomeImage = incomeImage;
    }

    @Override
    public Map<Color, InformationalImage> getPatterns() {
        return patterns;
    }

    @Override
    public void setPatterns(Map<Color, InformationalImage> patterns) {
        this.patterns = patterns;
    }

    @Override
    public Integer getExpectedColumnsNumber() {
        return expectedColumnsNumber;
    }

    @Override
    public void setExpectedColumnsNumber(Integer expectedColumnsNumber) {
        this.expectedColumnsNumber = expectedColumnsNumber;
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
    public String getImageFileName() {
        return imageFileName;
    }

    @Override
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
