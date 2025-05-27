package viewPackage;

import controllerPackage.ConnectionController;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel implements Panel {
    private final ConnectionController connectionController = new ConnectionController();

    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
    }
}
