import javax.swing.*;
import java.awt.*;

public class ProfilePanel extends JPanel {
    private JTextField usernameField;
    private JTextField schoolField;
    private JTextField sessionNameField;
    private SessionController sessionController;

    public ProfilePanel(SessionController sessionController) {
        this.sessionController = sessionController;
        setLayout(new BorderLayout());
        buildPanel();
    }

    private void buildPanel() {
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("School:"));
        schoolField = new JTextField();
        formPanel.add(schoolField);

        formPanel.add(new JLabel("Session Name:"));
        sessionNameField = new JTextField();
        formPanel.add(sessionNameField);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(formPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    public boolean validateAndSave() {
        String username = usernameField.getText().trim();
        String school = schoolField.getText().trim();
        String sessionName = sessionNameField.getText().trim();

        if (username.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please enter your username to continue.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (school.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please enter your school to continue.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (sessionName.isEmpty()) {

            JOptionPane.showMessageDialog(null, "Please enter your session name to continue.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        sessionController.getUserProfile().setUsername(username);
        sessionController.getUserProfile().setSchool(school);
        sessionController.getUserProfile().setSessionName(sessionName);
        return true;
    }
}