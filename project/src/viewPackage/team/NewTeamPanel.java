package viewPackage.team;

import viewPackage.Panel;

import javax.swing.*;
import java.awt.*;

public class NewTeamPanel extends JPanel implements Panel {
    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
    }
}