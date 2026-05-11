import javax.swing.*;
import java.awt.*;

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
        add(new JLabel("<html><h3>Step 5: Analyse</h3>Results and Gap Analysis based on collected data.</html>"), BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(resultsPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updatePanelData() {
        resultsPanel.removeAll();
        Scenario scenario = sessionController.getSelectedScenario();

        if (scenario != null) {
            String lowestDimensionName = "";
            double lowestScore = 5.1;

            JPanel barsPanel = new JPanel();
            barsPanel.setLayout(new BoxLayout(barsPanel, BoxLayout.Y_AXIS));
            barsPanel.setBorder(BorderFactory.createTitledBorder("Dimension Scores"));

            for (Dimension d : scenario.getDimensions()) {
                double totalMetricScoreTimesCoeff = 0;
                double totalMetricCoeff = 0;

                for (Metric m : d.getMetrics()) {
                    totalMetricScoreTimesCoeff += (m.getScore() * m.getCoefficient());
                    totalMetricCoeff += m.getCoefficient();
                }

                double dimensionScore = totalMetricScoreTimesCoeff / totalMetricCoeff;

                if (dimensionScore < lowestScore) {
                    lowestScore = dimensionScore;
                    lowestDimensionName = d.getName();
                }

                JPanel dimPanel = new JPanel(new BorderLayout());
                dimPanel.add(new JLabel(d.getName() + " (" + String.format("%.2f", dimensionScore) + ")  "), BorderLayout.WEST);

                JProgressBar bar = new JProgressBar(10, 50);
                bar.setValue((int)(dimensionScore * 10));
                bar.setStringPainted(true);
                bar.setString(String.format("%.2f / 5.0", dimensionScore));
                dimPanel.add(bar, BorderLayout.CENTER);

                barsPanel.add(dimPanel);
                barsPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
            }

            resultsPanel.add(barsPanel);
            resultsPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 20)));

            JPanel gapPanel = new JPanel();
            gapPanel.setLayout(new BoxLayout(gapPanel, BoxLayout.Y_AXIS));
            gapPanel.setBorder(BorderFactory.createTitledBorder("Gap Analysis"));

            double gapValue = 5.0 - lowestScore;
            String label = "Poor";
            if(lowestScore >= 4) label = "Excellent";
            else if(lowestScore >= 3) label = "Good";
            else if(lowestScore >= 2) label = "Needs Improvement";

            gapPanel.add(new JLabel("Lowest Dimension: " + lowestDimensionName + " (" + String.format("%.2f", lowestScore) + ")"));
            gapPanel.add(new JLabel("Gap Value: " + String.format("%.2f", gapValue)));
            gapPanel.add(new JLabel("Status: " + label));

            JLabel adviceLabel = new JLabel("<html><b>This dimension has the lowest score and requires the most improvement.</b></html>");
            adviceLabel.setForeground(Color.RED);
            gapPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 5)));
            gapPanel.add(adviceLabel);

            resultsPanel.add(gapPanel);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}