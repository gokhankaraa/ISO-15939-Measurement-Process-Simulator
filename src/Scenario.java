import java.util.ArrayList;
import java.util.List;

public class Scenario {
    private String qualityType;
    private String mode;
    private String scenarioName;
    private List<Dimension> dimensions;

    public Scenario(String qualityType, String mode, String scenarioName) {
        this.qualityType = qualityType;
        this.mode = mode;
        this.scenarioName = scenarioName;
        this.dimensions = new ArrayList<>();
    }

    public void addDimension(Dimension dimension) {
        this.dimensions.add(dimension);
    }

    public String getQualityType() { return qualityType; }
    public void setQualityType(String qualityType) { this.qualityType = qualityType; }
    public String getMode() { return mode; }
    public void setMode(String mode) { this.mode = mode; }
    public String getScenarioName() { return scenarioName; }
    public void setScenarioName(String scenarioName) { this.scenarioName = scenarioName; }
    public List<Dimension> getDimensions() { return dimensions; }
    public void setDimensions(List<Dimension> dimensions) { this.dimensions = dimensions; }
}