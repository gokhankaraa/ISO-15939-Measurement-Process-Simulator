import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScenarioDataStore {

    public static Map<String, List<Scenario>> getAllScenarios() {
        Map<String, List<Scenario>> scenarioMap = new HashMap<>();

        List<Scenario> eduScenarios = new ArrayList<>();

        Scenario eduScenC = new Scenario("Product Quality", "Education", "Scenario C - Team Alpha");
        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score", 50, "Higher↑", "0-100", "points"));
        usability.addMetric(new Metric("Onboarding time", 50, "Lower↓", "0-60", "min"));
        eduScenC.addDimension(usability);

        Dimension perfEfficiency = new Dimension("Perf. Efficiency", 20);
        perfEfficiency.addMetric(new Metric("Video start time", 50, "Lower↓", "0-15", "sec"));
        perfEfficiency.addMetric(new Metric("Concurrent exams", 50, "Higher↑", "0-600", "users"));
        eduScenC.addDimension(perfEfficiency);

        Dimension accessibility = new Dimension("Accessibility", 20);
        accessibility.addMetric(new Metric("WCAG compliance", 50, "Higher↑", "0-100", "%"));
        accessibility.addMetric(new Metric("Screen reader score", 50, "Higher↑", "0-100", "%"));
        eduScenC.addDimension(accessibility);

        Dimension reliability = new Dimension("Reliability", 20);
        reliability.addMetric(new Metric("Uptime", 50, "Higher↑", "95-100", "%"));
        reliability.addMetric(new Metric("MTTR", 50, "Lower↓", "0-120", "min"));
        eduScenC.addDimension(reliability);

        Dimension funcSuitability = new Dimension("Func. Suitability", 15);
        funcSuitability.addMetric(new Metric("Feature completion", 50, "Higher↑", "0-100", "%"));
        funcSuitability.addMetric(new Metric("Assignment submit rate", 50, "Higher↑", "0-100", "%"));
        eduScenC.addDimension(funcSuitability);

        Scenario eduScenD = new Scenario("Process Quality", "Education", "Scenario D - Team Beta");
        Dimension sprint = new Dimension("Sprint Efficiency", 100);
        sprint.addMetric(new Metric("Velocity", 100, "Higher↑", "0-100", "points"));
        eduScenD.addDimension(sprint);

        eduScenarios.add(eduScenC);
        eduScenarios.add(eduScenD);
        scenarioMap.put("Education", eduScenarios);

        List<Scenario> healthScenarios = new ArrayList<>();

        Scenario healthScenA = new Scenario("Product Quality", "Health", "Scenario A - Patient Portal");
        Dimension security = new Dimension("Security", 100);
        security.addMetric(new Metric("Data Breaches", 100, "Lower↓", "0-10", "incidents"));
        healthScenA.addDimension(security);

        Scenario healthScenB = new Scenario("Process Quality", "Health", "Scenario B - Core API");
        Dimension codeQual = new Dimension("Code Quality", 100);
        codeQual.addMetric(new Metric("Test Coverage", 100, "Higher↑", "0-100", "%"));
        healthScenB.addDimension(codeQual);

        healthScenarios.add(healthScenA);
        healthScenarios.add(healthScenB);
        scenarioMap.put("Health", healthScenarios);

        scenarioMap.put("Custom", new ArrayList<>());

        return scenarioMap;
    }
}