package viewPackage;

import controllerPackage.ConnectionController;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel implements Panel {
    private final PanelManager panelManager;
    private final ConnectionController connectionController;
    public HomePanel(PanelManager panelManager, ConnectionController connectionController) {
        this.panelManager = panelManager;
        this.connectionController = connectionController;
    }

    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
    }
}
