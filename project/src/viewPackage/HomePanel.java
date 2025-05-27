package viewPackage;

import controllerPackage.ConnectionController;
import exceptionPackage.CantConnectToDbException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel implements Panel {
    private final ConnectionController connectionController = new ConnectionController();

    @Override
    public void enterPanel() {
        JLabel text = new JLabel("<html>Application faite en Java, <br>pour le cours de Bac 2 \"Projet Intégré\", <br>donné par Mme DUBISY et <br>M. Bouraada.<br><br></html>");
        text.setEnabled(false);
        text.setFont(text.getFont().deriveFont(28f));
        this.add(text);
        JButton loginButton = new JButton("Login to Database");
        if(connectionController.getInstance() != null) {
            loginButton.setText("Access Granted.");
            loginButton.setEnabled(false);
        }
        loginButton.addActionListener(e -> {
            JPanel panel = new JPanel();
            JLabel label = new JLabel("Password:");
            JPasswordField passwordField = new JPasswordField(15);
            panel.add(label);
            panel.add(passwordField);
            String[] options = new String[]{"Ok", "Cancel"};
            int option = JOptionPane.showOptionDialog(null, panel, "DB Login", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
            if (option == 0) {
                char[] password = passwordField.getPassword();
                try {
                    connectionController.databaseLogin(String.valueOf(password));
                    loginButton.setText("Access Granted.");
                    loginButton.setEnabled(false);
                } catch (CantConnectToDbException ex) {
                    JOptionPane.showMessageDialog(null, "Incorrect Password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.add(loginButton);
    }
}
