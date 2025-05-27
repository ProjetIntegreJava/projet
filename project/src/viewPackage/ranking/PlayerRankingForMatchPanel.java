package viewPackage.ranking;

import controllerPackage.PlayerRankingForMatchController;
import exceptionPackage.PlayerRankingForMatch.ReadPlayerRankingForMatchException;
import modelPackage.Match;
import modelPackage.Participation;
import viewPackage.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerRankingForMatchPanel extends JPanel implements Panel {
    private final PlayerRankingForMatchController rankingController = new PlayerRankingForMatchController();
    private ArrayList<Match> matches;
    private JComboBox<Match> matchComboBox;
    private JTable rankingTable;

    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());

        // Top panel for match selection
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel matchLabel = new JLabel("Choix du match : ");
        matchComboBox = new JComboBox<>();
        JButton validateButton = new JButton("Valider");

        // Populate matchComboBox
        try {
            matches = new ArrayList<>(); // Replace with actual match fetching logic
            for (Match match : matches) {
                matchComboBox.addItem(match);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des matchs : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        topPanel.add(matchLabel);
        topPanel.add(matchComboBox);
        topPanel.add(validateButton);
        this.add(topPanel, BorderLayout.NORTH);

        // Center panel for ranking table
        JPanel centerPanel = new JPanel(new BorderLayout());
        rankingTable = new JTable();
        centerPanel.add(new JScrollPane(rankingTable), BorderLayout.CENTER);
        this.add(centerPanel, BorderLayout.CENTER);

        // Button action listener
        validateButton.addActionListener(e -> {
            Match selectedMatch = (Match) matchComboBox.getSelectedItem();
            if (selectedMatch != null) {
                try {
                    ArrayList<Participation> rankings = rankingController.getPlayerRankingForMatch(selectedMatch.getId());
                    String[] columnNames = {"Pseudo", "Role", "Champion", "Kills", "Assists", "Deaths", "Creep Score", "Damage", "Ward Score", "Gold Earned", "Damage Received"};
                    Object[][] data = new Object[rankings.size()][columnNames.length];
                    for (int i = 0; i < rankings.size(); i++) {
                        Participation p = rankings.get(i);
                        data[i][0] = p.getPlayer().getPseudo();
                        data[i][1] = p.getRole().getName();
                        data[i][2] = p.getChampion().getName();
                        data[i][3] = p.getKills();
                        data[i][4] = p.getAssists();
                        data[i][5] = p.getDeath();
                        data[i][6] = p.getCreepScore();
                        data[i][7] = p.getDamage();
                        data[i][8] = p.getWardScore();
                        data[i][9] = p.getGoldEarn();
                        data[i][10] = p.getDamageReceived();
                    }
                    rankingTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                } catch (ReadPlayerRankingForMatchException ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la récupération du classement : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un match.", "Erreur", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}

/*package viewPackage.ranking;

import controllerPackage.MatchController;
import controllerPackage.PlayerRankingForMatchController;
import exceptionPackage.Match.ReadMatchException;
import modelPackage.Match;
import modelPackage.Participation;
import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class PlayerRankingForMatchPanel extends JPanel implements Panel {
    private final PlayerRankingForMatchController playerRankingForMatchController = new PlayerRankingForMatchController();
    private final MatchController matchController = new MatchController();
    private ArrayList<Match> matches;
    private JTable rankingTable;

    @Override
    public void enterPanel(){

    }


    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
        JPanel chooseMatchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel("Choix match : ");

        DefaultListModel<String> listModel = new DefaultListModel<>();
        try {
            matches = matchController.getAllMatchs();
            for (Match match : matches) {
                listModel.addElement(match.getTeamBlue().getName() + " vs " + match.getTeamRed().getName() + " - " + match.getCompetition().getName() + " (" + match.getOccurenceDate() + ")");
            }
        } catch (ReadMatchException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des matchs : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        JPanel rankingPanel = new JPanel(new BorderLayout());
        this.add(rankingPanel);
        this.rankingTable = new JTable();
        rankingPanel.add(new JScrollPane(rankingTable));

        matchList = new JList<>(listModel);
        matchList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        matchList.addListSelectionListener(e ->  {
            Match selectedMatch = (Match) matchList.getSelectedValue();
        });

        JScrollPane scrollPane = new JScrollPane(matchList);
        scrollPane.setPreferredSize(new Dimension(300, 150));

        chooseMatchPanel.add(label);
        chooseMatchPanel.add(scrollPane);

        this.add(chooseMatchPanel, BorderLayout.NORTH);

        this.validate();
        this.repaint();
    }
       private class MatchSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                int selectedIndex = matchList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Match selectedMatch = matches.get(selectedIndex);
                    try {
                        ArrayList<Participation> ranking = rankingController.getPlayerRankingForMatch(selectedMatch.getId());

                        // Build the ranking display
                        StringBuilder rankingDisplay = new StringBuilder("Classement des joueurs :\n");
                        for (Participation participation : ranking) {
                            rankingDisplay.append(participation.getPlayer().getPseudo())
                                    .append(" (Role: ").append(participation.getRole().getName())
                                    .append(", Champion: ").append(participation.getChampion().getName())
                                    .append(", Kills: ").append(participation.getKills())
                                    .append(", Assists: ").append(participation.getAssists())
                                    .append(", Deaths: ").append(participation.getDeath())
                                    .append(", Creep Score: ").append(participation.getCreepScore())
                                    .append(", Damage: ").append(participation.getDamage())
                                    .append(", Ward Score: ").append(participation.getWardScore())
                                    .append(", Gold Earned: ").append(participation.getGoldEarn())
                                    .append(", Damage Received: ").append(participation.getDamageReceived())
                                    .append(")\n");
                        }

                        // Display the ranking
                        JOptionPane.showMessageDialog(PlayerRankingForMatchPanel.this, rankingDisplay.toString(), "Classement", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(PlayerRankingForMatchPanel.this, "Erreur lors de la récupération du classement : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }*/

