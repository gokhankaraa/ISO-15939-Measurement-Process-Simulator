import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class DefinePanel extends JPanel {
    private SessionController sessionController;

    private JRadioButton productRadio, processRadio;
    private JRadioButton customRadio, healthRadio, educationRadio;
    private JComboBox<String> scenarioComboBox;

    private Map<String, List<Scenario>> scenarioMap;

    public DefinePanel(SessionController sessionController) {
        this.sessionController = sessionController;
        this.scenarioMap = ScenarioDataStore.getAllScenarios();
        setLayout(new BorderLayout());
        buildPanel();
    }

    private void buildPanel() {
        JPanel mainContent = new JPanel(new GridLayout(3, 1, 10, 20));
        mainContent.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.setBorder(BorderFactory.createTitledBorder("2a. Quality Type"));
        productRadio = new JRadioButton("Product Quality");
        processRadio = new JRadioButton("Process Quality");
        ButtonGroup typeGroup = new ButtonGroup();
        typeGroup.add(productRadio);
        typeGroup.add(processRadio);
        typePanel.add(productRadio);
        typePanel.add(processRadio);
        productRadio.setSelected(true);

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        modePanel.setBorder(BorderFactory.createTitledBorder("2b. Mode"));
        customRadio = new JRadioButton("Custom");
        healthRadio = new JRadioButton("Health");
        educationRadio = new JRadioButton("Education");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(customRadio);
        modeGroup.add(healthRadio);
        modeGroup.add(educationRadio);
        modePanel.add(customRadio);
        modePanel.add(healthRadio);
        modePanel.add(educationRadio);

        JPanel scenarioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        scenarioPanel.setBorder(BorderFactory.createTitledBorder("2c. Scenario"));
        scenarioComboBox = new JComboBox<>();
        scenarioComboBox.setPreferredSize(new java.awt.Dimension(250, 25));
        scenarioPanel.add(new JLabel("Select Scenario: "));
        scenarioPanel.add(scenarioComboBox);

        educationRadio.addActionListener(e -> updateScenarios("Education"));
        healthRadio.addActionListener(e -> updateScenarios("Health"));
        customRadio.addActionListener(e -> updateScenarios("Custom"));

        mainContent.add(typePanel);
        mainContent.add(modePanel);
        mainContent.add(scenarioPanel);

        add(mainContent, BorderLayout.CENTER);
    }

    private void updateScenarios(String mode) {
        scenarioComboBox.removeAllItems();

        List<Scenario> modeScenarios = scenarioMap.get(mode);

        if (modeScenarios != null && !modeScenarios.isEmpty()) {
            for (Scenario s : modeScenarios) {
                scenarioComboBox.addItem(s.getScenarioName());
            }
        } else if (mode.equals("Custom")) {
            scenarioComboBox.addItem("Custom Scenario (Not Implemented)");
        }
    }

    public boolean validateAndSave() {
        if (!productRadio.isSelected() && !processRadio.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select a Quality Type.");
            return false;
        }

        String selectedMode = "";
        if (customRadio.isSelected()) selectedMode = "Custom";
        else if (healthRadio.isSelected()) selectedMode = "Health";
        else if (educationRadio.isSelected()) selectedMode = "Education";

        if (selectedMode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a Mode.");
            return false;
        }

        if (scenarioComboBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a Scenario.");
            return false;
        }

        String selectedScenarioName = (String) scenarioComboBox.getSelectedItem();

        if (!selectedMode.equals("Custom")) {
            List<Scenario> currentList = scenarioMap.get(selectedMode);
            for (Scenario s : currentList) {
                if (s.getScenarioName().equals(selectedScenarioName)) {
                    sessionController.setSelectedScenario(s);
                    break;
                }
            }
        }

        return true;
    }
}