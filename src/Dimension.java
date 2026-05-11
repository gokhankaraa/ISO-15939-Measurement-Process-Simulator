import java.util.ArrayList;
import java.util.List;

public class Dimension {
    private String name;
    private int coefficient;
    private List<Metric> metrics;

    public Dimension(String name, int coefficient) {
        this.name = name;
        this.coefficient = coefficient;
        this.metrics = new ArrayList<>();
    }

    public void addMetric(Metric metric) {
        this.metrics.add(metric);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCoefficient() { return coefficient; }
    public void setCoefficient(int coefficient) { this.coefficient = coefficient; }
    public List<Metric> getMetrics() { return metrics; }
    public void setMetrics(List<Metric> metrics) { this.metrics = metrics; }
}