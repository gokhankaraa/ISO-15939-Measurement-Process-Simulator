
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlanPanel extends JPanel {
    private SessionController sessionController;
    private JTable planTable;
    private DefaultTableModel tableModel;

    public PlanPanel(SessionController sessionController) {
        this.sessionController = sessionController;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buildPanel();
    }

    private void buildPanel() {
        add(new JLabel("<html><h3>Step 3: Plan Measurement</h3>Review the predefined metrics for your scenario.</html>"), BorderLayout.NORTH);

        String[] columnNames = {"Dimension / Metric", "Coefficient", "Direction", "Range", "Unit"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        planTable = new JTable(tableModel);
        planTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(planTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void updatePanelData() {
        tableModel.setRowCount(0);
        Scenario scenario = sessionController.getSelectedScenario();

        if (scenario != null) {
            for (Dimension d : scenario.getDimensions()) {
                tableModel.addRow(new Object[]{"[" + d.getName() + "]", d.getCoefficient() + " (Dim)", "", "", ""});

                for (Metric m : d.getMetrics()) {
                    tableModel.addRow(new Object[]{
                            "  - " + m.getName(),
                            m.getCoefficient(),
                            m.getDirection(),
                            m.getRange(),
                            m.getUnit()
                    });
                }
            }
        }
    }
}