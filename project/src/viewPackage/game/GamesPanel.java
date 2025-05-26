package viewPackage.game;

import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import java.awt.*;

public class GamesPanel extends JPanel implements Panel {
    private PanelManager panelManager;
    public GamesPanel(PanelManager panelManager) {
        this.panelManager = panelManager;
    }

    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
    }

}
