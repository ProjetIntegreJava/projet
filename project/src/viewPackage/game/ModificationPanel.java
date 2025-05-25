package viewPackage.game;

import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import java.awt.*;

public class ModificationPanel extends JPanel implements Panel {
    private PanelManager panelManager;
    public ModificationPanel(PanelManager panelManager) {
        this.panelManager = panelManager;
    }

    @Override
    public void enterPanel() {

    }

    @Override
    public void init() {
        this.setLayout(new BorderLayout());
    }
}
