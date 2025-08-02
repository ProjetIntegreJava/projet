package viewPackage.ranking;

import controllerPackage.MatchController;
import controllerPackage.PlayerRankingForMatchController;
import exceptionPackage.Match.ReadMatchException;
import exceptionPackage.PlayerRankingForMatch.ReadPlayerRankingForMatchException;
import modelPackage.Match;
import modelPackage.Participation;
import viewPackage.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerRankingForMatchPanel extends JPanel implements Panel {
    private final PlayerRankingForMatchController rankingController = new PlayerRankingForMatchController();
    private final MatchController matchController = new MatchController();
    private ArrayList<Match> matches;
    private JComboBox<Match> matchComboBox;
    private JTable rankingTable;

    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel matchLabel = new JLabel("Choix du match : ");
        matchComboBox = new JComboBox<>();
        matchComboBox.setPreferredSize(new Dimension(200, 25));
        JButton validateButton = new JButton("Valider");

        try {
            matches = new ArrayList<>();
            matches = matchController.getAllMatches();
            for (Match match : matches) {
                matchComboBox.addItem(match);
            }
        } catch (ReadMatchException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des matchs ", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        topPanel.add(matchLabel);
        topPanel.add(matchComboBox);
        topPanel.add(validateButton);
        this.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        rankingTable = new JTable();
        centerPanel.add(new JScrollPane(rankingTable), BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        validateButton.addActionListener(e -> {
            Match selectedMatch = (Match) matchComboBox.getSelectedItem();
            if (selectedMatch != null) {
                try {
                    System.out.println("Récupération du classement pour le match : " + selectedMatch.getId());
                    ArrayList<Participation> rankings = rankingController.getPlayerRankingForMatch(selectedMatch.getId());
                    String[] columnNames = {"Score", "Pseudo", "Role", "Champion", "Kills", "Assists", "Deaths", "Creep Score", "Damage", "Ward Score", "Gold Earned", "Damage Received"};
                    Object[][] data = new Object[rankings.size()][columnNames.length];
                    for (int i = 0; i < rankings.size(); i++) {
                        Participation p = rankings.get(i);
                        data[i][0] = p.getScore();
                        data[i][1] = p.getPlayer().getPseudo();
                        data[i][2] = p.getRole().getName();
                        data[i][3] = p.getChampion().getName();
                        data[i][4] = p.getKills();
                        data[i][5] = p.getAssists();
                        data[i][6] = p.getDeath();
                        data[i][7] = p.getCreepScore();
                        data[i][8] = p.getDamage();
                        data[i][9] = p.getWardScore();
                        data[i][10] = p.getGoldEarn();
                        data[i][11] = p.getDamageReceived();
                    }
                    rankingTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                } catch (ReadPlayerRankingForMatchException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la récupération du classement", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un match.", "Erreur", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}