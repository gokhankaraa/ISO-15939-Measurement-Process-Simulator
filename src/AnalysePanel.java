import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class AnalysePanel extends JPanel {
    private final SessionController sessionController;
    private JPanel resultsPanel;

    public AnalysePanel(SessionController sessionController) {
        this.sessionController = sessionController;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buildPanel();
    }

    private void buildPanel() {
        add(new JLabel("<html><h3>Step 5: Analyse</h3>Weighted Dimension Scores and Radar Chart Visualization.</html>"), BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updatePanelData() {
        resultsPanel.removeAll();
        Scenario scenario = sessionController.getSelectedScenario();

        if (scenario != null) {
            String lowestDimensionName = "";
            double lowestScore = 5.1;

            Map<String, Double> chartData = new LinkedHashMap<>();

            JPanel barsPanel = new JPanel();
            barsPanel.setLayout(new BoxLayout(barsPanel, BoxLayout.Y_AXIS));
            barsPanel.setBorder(BorderFactory.createTitledBorder("Calculated Dimension Scores"));

            for (Dimension d : scenario.getDimensions()) {
                double totalMetricScoreTimesCoeff = 0;
                double totalMetricCoeff = 0;

                for (Metric m : d.getMetrics()) {
                    totalMetricScoreTimesCoeff += (m.getScore() * m.getCoefficient());
                    totalMetricCoeff += m.getCoefficient();
                }

                double dimensionScore = totalMetricScoreTimesCoeff / totalMetricCoeff;

                chartData.put(d.getName(), dimensionScore);

                if (dimensionScore < lowestScore) {
                    lowestScore = dimensionScore;
                    lowestDimensionName = d.getName();
                }

                JPanel dimPanel = new JPanel(new BorderLayout(10, 0));
                JLabel nameLabel = new JLabel(d.getName() + ": ");
                nameLabel.setPreferredSize(new java.awt.Dimension(120, 25));
                dimPanel.add(nameLabel, BorderLayout.WEST);

                JProgressBar bar = new JProgressBar(0, 50);
                bar.setValue((int)(dimensionScore * 10));
                bar.setStringPainted(true);
                bar.setString(String.format("%.2f / 5.0", dimensionScore));
                bar.setForeground(new Color(100, 149, 237));
                dimPanel.add(bar, BorderLayout.CENTER);

                barsPanel.add(dimPanel);
                barsPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
            }

            resultsPanel.add(barsPanel);
            resultsPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 20)));

            if (chartData.size() >= 3) {
                JPanel chartPanel = new JPanel(new BorderLayout());
                TitledBorder border = BorderFactory.createTitledBorder("Radar Chart View");
                border.setTitleJustification(TitledBorder.CENTER);
                chartPanel.setBorder(border);

                RadarChartComponent radarChart = new RadarChartComponent(chartData);
                chartPanel.add(radarChart, BorderLayout.CENTER);

                resultsPanel.add(chartPanel);
                resultsPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 20)));
            }

            JPanel gapPanel = new JPanel();
            gapPanel.setLayout(new BoxLayout(gapPanel, BoxLayout.Y_AXIS));
            gapPanel.setBorder(BorderFactory.createTitledBorder("Gap Analysis (ISO 15939)"));

            double gapValue = 5.0 - lowestScore;

            JLabel lowLabel = new JLabel("<html><b>Lowest Scoring Dimension:</b> <span style='color:blue'>" + lowestDimensionName + " (" + String.format("%.2f", lowestScore) + ")</span></html>");
            gapPanel.add(lowLabel);

            JLabel gapValLabel = new JLabel("<html><b>Calculated Gap (vs 5.0 Target):</b> " + String.format("%.2f", gapValue) + "</html>");
            gapPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
            gapPanel.add(gapValLabel);

            JLabel adviceLabel = new JLabel("<html><b><span style='color:red'>WARNING: This dimension has the lowest score and requires the most improvement.</span></b></html>");
            gapPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 15)));
            gapPanel.add(adviceLabel);

            resultsPanel.add(gapPanel);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}