package viewPackage.ranking;

import controllerPackage.MatchController;
import modelPackage.Match;
import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RankingPanel extends JPanel implements Panel {
    private final PanelManager panelManager;
    private final MatchController matchController;

    public RankingPanel(PanelManager panelManager) {
        this.panelManager = panelManager;
        this.matchController = new MatchController();
    }

    @Override
    public void enterPanel() {
        this.removeAll();
        this.setLayout(new BorderLayout());

        // Create the top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel("Choix match : ");

        // Fetch matches and populate combo box
        JComboBox<String> comboBox = new JComboBox<>();
        try {
            ArrayList<Match> matches = matchController.getAllMatches();
            for (Match match : matches) {
                comboBox.addItem(match.getTeamBlue().getName() + " vs " + match.getTeamRed().getName() + " - " + match.getCompetition().getName() + " (" + match.getOccurrenceDate() + ")");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des matchs : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        // Add components to the top panel
        topPanel.add(label);
        topPanel.add(comboBox);

        // Add the top panel to the main panel
        this.add(topPanel, BorderLayout.NORTH);

        this.validate();
        this.repaint();
    }
}