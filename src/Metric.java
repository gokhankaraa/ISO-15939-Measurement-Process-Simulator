public class Metric {
    private String name;
    private int coefficient;
    private String direction;
    private String range;
    private String unit;
    private double value;
    private double score;

    public Metric(String name, int coefficient, String direction, String range, String unit) {
        this.name = name;
        this.coefficient = coefficient;
        this.direction = direction;
        this.range = range;
        this.unit = unit;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCoefficient() { return coefficient; }
    public void setCoefficient(int coefficient) { this.coefficient = coefficient; }
    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
    public String getRange() { return range; }
    public void setRange(String range) { this.range = range; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
}