import javax.swing.*;
import java.awt.*;

public class StepIndicatorPanel extends JPanel {
    private String[] steps = {"Profile", "Define", "Plan", "Collect", "Analyse"};
    private int currentStep = 0;

    public StepIndicatorPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        updateIndicators();
    }

    // MainFrame'in sayfayı değiştirirken çağırdığı eksik metot
    public void setCurrentStep(int stepIndex) {
        this.currentStep = stepIndex;
        updateIndicators();
    }

    private void updateIndicators() {
        removeAll();
        for (int i = 0; i < steps.length; i++) {
            JLabel stepLabel = new JLabel(steps[i]);
            if (i < currentStep) {
                stepLabel.setText(steps[i] + " \u2713"); // Tamamlananlar için tik işareti
                stepLabel.setForeground(new Color(0, 150, 0)); // Yeşil
            } else if (i == currentStep) {
                stepLabel.setFont(stepLabel.getFont().deriveFont(Font.BOLD));
                stepLabel.setForeground(Color.BLUE); // Aktif adım mavi ve kalın
            } else {
                stepLabel.setForeground(Color.GRAY); // Gelecek adımlar gri
            }
            add(stepLabel);

            if (i < steps.length - 1) {
                add(new JLabel(" \u2192 ")); // Adımlar arası ok işareti
            }
        }
        revalidate();
        repaint();
    }
}