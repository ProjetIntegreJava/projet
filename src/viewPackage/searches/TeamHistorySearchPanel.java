package viewPackage.searches;

import controllerPackage.SearchController;
import controllerPackage.TeamController;
import exceptionPackage.Team.ReadTeamException;
import modelPackage.Competition;
import modelPackage.Team;
import modelPackage.searches.ResultMatchData;
import modelPackage.searches.ResultTeamHistoryData;
import viewPackage.Panel;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TeamHistorySearchPanel extends JPanel implements Panel {
    private final SearchController searchController = new SearchController();
    private final TeamController teamController = new TeamController();
    private JTable resultTable;

    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
        JPanel formPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        formPanel.setLayout(gridBag);
        ArrayList<Team> teams = new ArrayList<>();
        try {
            teams = this.teamController.getAllTeams();
        } catch (ReadTeamException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        JLabel teamLabel = new JLabel("Teams*: ");
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        gridBag.setConstraints(teamLabel, c);
        formPanel.add(teamLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JComboBox<Team> teamComboBox = new JComboBox<>(teams.toArray(new Team[10]));
        gridBag.setConstraints(teamComboBox, c);
        formPanel.add(teamComboBox);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        JLabel beginningDateLabel = new JLabel("Beginning Date*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(beginningDateLabel, c);
        formPanel.add(beginningDateLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JSpinner beginningDateSpinner = new JSpinner(new SpinnerDateModel());
        beginningDateSpinner.setEditor(new JSpinner.DateEditor(beginningDateSpinner, formatter.toPattern()));
        beginningDateSpinner.setValue(new Date(119, Calendar.JANUARY, 1));
        gridBag.setConstraints(beginningDateSpinner, c);
        formPanel.add(beginningDateSpinner);

        JLabel endingDateLabel = new JLabel("Ending Date*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(endingDateLabel, c);
        formPanel.add(endingDateLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JSpinner endingDateSpinner = new JSpinner(new SpinnerDateModel());
        endingDateSpinner.setEditor(new JSpinner.DateEditor(endingDateSpinner, formatter.toPattern()));
        gridBag.setConstraints(endingDateSpinner, c);
        formPanel.add(endingDateSpinner);
        this.add(formPanel, BorderLayout.NORTH);

        JPanel resultPanel = new JPanel(new BorderLayout());
        this.add(resultPanel, BorderLayout.CENTER);
        this.resultTable = new JTable();
        resultPanel.add(new JScrollPane(resultTable));

        JPanel buttonPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            Team selectedTeam = (Team) teamComboBox.getSelectedItem();
            LocalDate beginningDate = ((Date) beginningDateSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endingDate = ((Date) endingDateSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (selectedTeam != null && beginningDate.isBefore(endingDate)) {
                try {
                    ArrayList<ResultTeamHistoryData> results = searchController.getSearchTeamHistoryData(selectedTeam.getName(), beginningDate, endingDate);
                    String[] columnNames = {
                            "Beginning Date", "Ending Date", "Team Name", "Club Name", "Nationality", "Player"
                    };
                    Object[][] data = new Object[results.size()][columnNames.length];
                    for (int i = 0; i < results.size(); i++) {
                        data[i][0] = results.get(i).getBeginningDate();
                        data[i][1] = results.get(i).getEndingDate();
                        data[i][2] = results.get(i).getTeamName();
                        data[i][3] = results.get(i).getClubName();
                        data[i][4] = results.get(i).getClubNationality();
                        data[i][5] = results.get(i).getPlayer();
                    }
                    resultTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                } catch (Exception ex) {
                    System.out.println("SelectedTeam: " + selectedTeam.getName());
                    System.out.println("BeginningDate: " + beginningDate);
                    System.out.println("EndingDate: " + endingDate);
                    JOptionPane.showMessageDialog(this, "Error fetching matches:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select both a champion and a competition.", "Input Error", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(searchButton);
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            teamComboBox.setSelectedIndex(0);
            resultTable.setModel(new javax.swing.table.DefaultTableModel());
        });
        buttonPanel.add(resetButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
