package viewPackage.game;

import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import java.awt.*;

public class NewGamePanel extends JPanel implements Panel {
    private PanelManager panelManager;
    public NewGamePanel(PanelManager panelManager) {
        this.panelManager = panelManager;
    }

    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
    }
}
