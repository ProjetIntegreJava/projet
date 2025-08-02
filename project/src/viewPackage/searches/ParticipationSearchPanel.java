package viewPackage.searches;

import controllerPackage.MatchController;
import controllerPackage.SearchController;
import exceptionPackage.Match.ReadMatchException;
import modelPackage.Champion;
import modelPackage.Match;
import modelPackage.searches.ResultParticipationData;
import viewPackage.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ParticipationSearchPanel extends JPanel implements Panel {
    private final MatchController matchController = new MatchController();
    private final SearchController searchController = new SearchController();
    private JTable resultTable;

    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
        JPanel formPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        formPanel.setLayout(gridBag);

        ArrayList<Match> matches = new ArrayList<>();
        try {
            matches = this.matchController.getAllMatches();
        } catch (ReadMatchException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        JLabel matchLabel = new JLabel("Matches*:");
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        gridBag.setConstraints(matchLabel, c);
        formPanel.add(matchLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JComboBox<Match> matchComboBox = new JComboBox<>(matches.toArray(new Match[0]));
        gridBag.setConstraints(matchComboBox, c);
        formPanel.add(matchComboBox);
        this.add(formPanel, BorderLayout.NORTH);

        JPanel resultPanel = new JPanel(new BorderLayout());
        this.add(resultPanel);
        this.resultTable = new JTable();
        resultPanel.add(new JScrollPane(resultTable));

        JPanel buttonPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            Match selectedMatch = (Match) matchComboBox.getSelectedItem();
            if (selectedMatch != null) {
                try {
                    ArrayList<ResultParticipationData> results = this.searchController.getSearchParticipationData(selectedMatch.getId());
                    String[] columnNames = {"Name","Champion Name", "Champion Race","K/D/A", "Creep Score", "Damage Dealt", "Damage Received", "Wards Placed", "Golds Earned", "Role"};
                    Object[][] data = new Object[results.size()][columnNames.length];
                    for (int i = 0; i < results.size(); i++) {
                        ResultParticipationData result = results.get(i);
                        data[i][0] = result.getFirstName() + " " + result.getLastName();
                        data[i][1] = result.getChampionName();
                        data[i][2] = result.getChampionRace();
                        data[i][3] = result.getKills() + "/" + result.getDeaths() + "/" + result.getAssists();
                        data[i][4] = result.getCreepScore();
                        data[i][5] = result.getDamage();
                        data[i][6] = result.getDamageReceived();
                        data[i][7] = result.getWardScore();
                        data[i][8] = result.getGoldEarned();
                        data[i][9] = result.getRole();
                    }
                    resultTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(searchButton);
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            matchComboBox.setSelectedIndex(0);
            resultTable.setModel(new javax.swing.table.DefaultTableModel());
        });
        buttonPanel.add(resetButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
