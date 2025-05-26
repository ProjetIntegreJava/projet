package viewPackage;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel implements Panel {
    private PanelManager panelManager;
    public HomePanel(PanelManager panelManager) {
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
