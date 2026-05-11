
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;

public class CollectPanel extends JPanel {
    private SessionController sessionController;
    private JTable collectTable;
    private DefaultTableModel tableModel;

    public CollectPanel(SessionController sessionController) {
        this.sessionController = sessionController;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buildPanel();
    }

    private void buildPanel() {
        add(new JLabel("<html><h3>Step 4: Collect Data</h3>Raw values are collected and scores (1-5) are calculated automatically.</html>"), BorderLayout.NORTH);

        String[] columnNames = {"Metric", "Direction", "Range", "Value", "Score (1-5)", "Coeff / Unit"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        collectTable = new JTable(tableModel);
        collectTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(collectTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updatePanelData() {
        tableModel.setRowCount(0);
        Scenario scenario = sessionController.getSelectedScenario();
        Random rand = new Random();

        if (scenario != null) {
            for (Dimension d : scenario.getDimensions()) {
                for (Metric m : d.getMetrics()) {
                    String[] parts = m.getRange().split("-");
                    double min = Double.parseDouble(parts[0]);
                    double max = Double.parseDouble(parts[1]);

                    double value = min + (max - min) * rand.nextDouble();
                    m.setValue(Math.round(value));

                    double score = 0;
                    if (m.getDirection().contains("Higher")) {
                        score = 1 + ((m.getValue() - min) / (max - min)) * 4;
                    } else if (m.getDirection().contains("Lower")) {
                        score = 5 - ((m.getValue() - min) / (max - min)) * 4;
                    }

                    score = Math.round(score * 2) / 2.0;
                    if(score > 5.0) score = 5.0;
                    if(score < 1.0) score = 1.0;
                    m.setScore(score);

                    tableModel.addRow(new Object[]{
                            m.getName(),
                            m.getDirection(),
                            m.getRange(),
                            String.format("%.1f", m.getValue()),
                            String.format("%.1f", m.getScore()),
                            m.getCoefficient() + " / " + m.getUnit()
                    });
                }
            }
        }
    }
}