package viewPackage.searches;

import controllerPackage.ChampionController;
import controllerPackage.CompetitionController;
import controllerPackage.SearchController;
import exceptionPackage.Champion.ReadChampionException;
import exceptionPackage.Competition.ReadCompetitionException;
import modelPackage.Champion;
import modelPackage.Competition;
import modelPackage.searches.ResultMatchData;
import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MatchSearchPanel extends JPanel implements Panel {
    private final ChampionController championController = new ChampionController();
    private final CompetitionController competitionController = new CompetitionController();
    private final SearchController searchController = new SearchController();
    private JTable resultTable;

    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
        JPanel formPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        formPanel.setLayout(gridBag);

        ArrayList<Champion> champions = new ArrayList<>();
        ArrayList<Competition> competitions = new ArrayList<>();
        try {
            champions = championController.getAllChampions();
            competitions = competitionController.getAllCompetitions();
        } catch (ReadChampionException | ReadCompetitionException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        JLabel championLabel = new JLabel("Champion Name*:");
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        gridBag.setConstraints(championLabel, c);
        formPanel.add(championLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JComboBox<String> championComboBox = new JComboBox<>(champions
                .stream()
                .map(Champion::getName)
                .toArray(String[]::new)
        );
        gridBag.setConstraints(championComboBox, c);
        formPanel.add(championComboBox);

        JLabel competitionLabel = new JLabel("Competition Name*:");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(competitionLabel, c);
        formPanel.add(competitionLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JComboBox<Competition> competitionComboBox = new JComboBox<>(competitions.toArray(new Competition[0]));
        gridBag.setConstraints(competitionComboBox, c);
        formPanel.add(competitionComboBox);
        this.add(formPanel, BorderLayout.NORTH);

        JPanel resultPanel = new JPanel(new BorderLayout());
        this.add(resultPanel);
        this.resultTable = new JTable();
        resultPanel.add(new JScrollPane(resultTable));

        JPanel buttonPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String selectedChampion = (String) championComboBox.getSelectedItem();
            Competition selectedCompetition = (Competition) competitionComboBox.getSelectedItem();
            if (selectedChampion != null && selectedCompetition != null) {
                try {
                    ArrayList<ResultMatchData> results = searchController.getSearchMatchData(selectedCompetition, selectedChampion);
                    String[] columnNames = {"Replay Link", "Winner", "Team Blue", "Club Blue", "Team Red", "Club Red", "Occurrence Date"};
                    Object[][] data = new Object[results.size()][columnNames.length];
                    for (int i = 0; i < results.size(); i++) {
                        ResultMatchData result = results.get(i);
                        data[i][0] = result.getReplayLink();
                        data[i][1] = result.isBlueWinner() ? "Blue" : "Red";
                        data[i][2] = result.getTeamBlueName();
                        data[i][3] = result.getClubBlueName();
                        data[i][4] = result.getTeamRedName();
                        data[i][5] = result.getClubRedName();
                        data[i][6] = result.getOccurrenceDate();
                    }
                    resultTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error fetching matches: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select both a champion and a competition.", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(searchButton);
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            championComboBox.setSelectedIndex(0);
            competitionComboBox.setSelectedIndex(0);
            resultTable.setModel(new javax.swing.table.DefaultTableModel());
        });
        buttonPanel.add(resetButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
