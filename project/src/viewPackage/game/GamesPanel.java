package viewPackage.game;

import viewPackage.Panel;

import javax.swing.*;
import java.awt.*;

public class GamesPanel extends JPanel implements Panel {

    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
    }
}
