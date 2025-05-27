package viewPackage.team;

import modelPackage.Team;
import viewPackage.Panel;

import javax.swing.*;
import java.awt.*;

public class ModificationTeamPanel extends JPanel implements Panel {
    private final Team selectedTeam;
    public ModificationTeamPanel(Team selectedTeam) {
        this.selectedTeam = selectedTeam;
    }
    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
    }
}
