import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private StepIndicatorPanel stepIndicatorPanel;
    private JPanel bottomPanel;
    private JButton backButton;
    private JButton nextButton;

    private SessionController sessionController;
    private int currentStepIndex = 0;
    private final int TOTAL_STEPS = 5;

    private ProfilePanel profilePanel;
    private DefinePanel definePanel;
    private PlanPanel planPanel;
    private CollectPanel collectPanel;
    private AnalysePanel analysePanel;

    public MainFrame() {
        sessionController = new SessionController();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        setTitle("ISO 15939 Measurement Process Simulator");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        stepIndicatorPanel = new StepIndicatorPanel();
        add(stepIndicatorPanel, BorderLayout.NORTH);

        setupPanels();
        add(mainPanel, BorderLayout.CENTER);

        setupBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        showStep(0);
    }

    private void setupPanels() {
        profilePanel = new ProfilePanel(sessionController);
        definePanel = new DefinePanel(sessionController);
        planPanel = new PlanPanel(sessionController);
        collectPanel = new CollectPanel(sessionController);
        analysePanel = new AnalysePanel(sessionController);

        mainPanel.add(profilePanel, "Step0");
        mainPanel.add(definePanel, "Step1");
        mainPanel.add(planPanel, "Step2");
        mainPanel.add(collectPanel, "Step3");
        mainPanel.add(analysePanel, "Step4");
    }

    private void setupBottomPanel() {
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        backButton = new JButton("Back");
        nextButton = new JButton("Next");

        backButton.addActionListener(e -> previousStep());
        nextButton.addActionListener(e -> nextStep());

        bottomPanel.add(backButton);
        bottomPanel.add(nextButton);
    }

    private void nextStep() {
        boolean canProceed = true;

        if (currentStepIndex == 0) {
            canProceed = profilePanel.validateAndSave();
        } else if (currentStepIndex == 1) {
            canProceed = definePanel.validateAndSave();
        } else if (currentStepIndex == TOTAL_STEPS - 1) {
            JOptionPane.showMessageDialog(this,
                    "Measurement process completed successfully!\nSession: " + sessionController.getUserProfile().getSessionName(),
                    "Process Complete",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (canProceed && currentStepIndex < TOTAL_STEPS - 1) {
            currentStepIndex++;
            showStep(currentStepIndex);
        }
    }

    private void previousStep() {
        if (currentStepIndex > 0) {
            currentStepIndex--;
            showStep(currentStepIndex);
        }
    }

    private void showStep(int index) {
        if (index == 2) {
            planPanel.updatePanelData();
        } else if (index == 3) {
            collectPanel.updatePanelData();
        } else if (index == 4) {
            analysePanel.updatePanelData();
        }

        cardLayout.show(mainPanel, "Step" + index);

        stepIndicatorPanel.setCurrentStep(index);

        backButton.setEnabled(index > 0);

        nextButton.setText(index == TOTAL_STEPS - 1 ? "Finish" : "Next");
    }
}