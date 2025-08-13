package viewPackage.match;

import controllerPackage.CompetitionController;
import controllerPackage.MatchController;
import controllerPackage.TeamController;
import modelPackage.Competition;
import modelPackage.Match;
import modelPackage.Team;
import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class ModificationMatchPanel extends JPanel implements Panel {
    private final MatchController matchController = new MatchController();
    private final TeamController teamController = new TeamController();
    private final CompetitionController competitionController = new CompetitionController();
    private final PanelManager panelManager;
    private final Match selectedMatch;
    public ModificationMatchPanel(Match selectedMatch, PanelManager panelManager) {
        this.selectedMatch = selectedMatch;
        this.panelManager = panelManager;
    }
    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        formPanel.setLayout(gridBag);

        ArrayList<Team> teams = new ArrayList<>();
        ArrayList<Competition> competitions = new ArrayList<>();
        try {
            teams = this.teamController.getAllTeams();
            competitions = this.competitionController.getAllCompetitions();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        JLabel teamStateLabel = new JLabel(selectedMatch.getTeamBlue() + " vs " + selectedMatch.getTeamRed());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        gridBag.setConstraints(teamStateLabel, c);
        formPanel.add(teamStateLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JLabel regionLabel = new JLabel(selectedMatch.getCompetition().toString());
        gridBag.setConstraints(regionLabel, c);
        formPanel.add(regionLabel);

        JLabel teamBlueNameLabel = new JLabel("Team Blue Name*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.weightx = 1.0;
        gridBag.setConstraints(teamBlueNameLabel, c);
        formPanel.add(teamBlueNameLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JComboBox<Team> teamBlueComboBox = new JComboBox<>(teams.toArray(new Team[0]));
        teamBlueComboBox.setSelectedItem(selectedMatch.getTeamBlue());
        gridBag.setConstraints(teamBlueComboBox, c);
        formPanel.add(teamBlueComboBox);

        JLabel teamRedNameLabel = new JLabel("Team Red Name*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(teamRedNameLabel, c);
        formPanel.add(teamRedNameLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JComboBox<Team> teamRedComboBox = new JComboBox<>(teams.toArray(new Team[0]));
        teamRedComboBox.setSelectedItem(selectedMatch.getTeamRed());
        gridBag.setConstraints(teamRedComboBox, c);
        formPanel.add(teamRedComboBox);

        JLabel competitionNameLabel = new JLabel("Competition*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(competitionNameLabel, c);
        formPanel.add(competitionNameLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JComboBox<Competition> competitionComboBox = new JComboBox<>(competitions.toArray(new Competition[0]));
        competitionComboBox.setSelectedItem(selectedMatch.getCompetition());
        gridBag.setConstraints(competitionComboBox, c);
        formPanel.add(competitionComboBox);

        JLabel occurrenceDateLabel = new JLabel("Occurrence Date*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(occurrenceDateLabel, c);
        formPanel.add(occurrenceDateLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        JSpinner occurrenceDateSpinner = new JSpinner(new SpinnerDateModel());
        occurrenceDateSpinner.setEditor(new JSpinner.DateEditor(occurrenceDateSpinner, formatter.toPattern()));
        occurrenceDateSpinner.setValue(Date.from(selectedMatch.getOccurrenceDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        gridBag.setConstraints(occurrenceDateSpinner, c);
        formPanel.add(occurrenceDateSpinner);

        JLabel replayLinkLabel = new JLabel("Replay Link: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(replayLinkLabel, c);
        formPanel.add(replayLinkLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JTextField replayLinkTextField = new JTextField(selectedMatch.getReplayLink(), 20);
        gridBag.setConstraints(replayLinkTextField, c);
        formPanel.add(replayLinkTextField);

        JLabel winnerLabel = new JLabel("Did Blue win?*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(winnerLabel, c);
        formPanel.add(winnerLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JCheckBox winnerCheckBox = new JCheckBox();
        winnerCheckBox.setSelected(selectedMatch.isBlueWin());
        gridBag.setConstraints(winnerCheckBox, c);
        formPanel.add(winnerCheckBox);

        JLabel summaryLabel = new JLabel("Match Summary: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(summaryLabel, c);
        formPanel.add(summaryLabel);
        JTextArea summaryTextArea = new JTextArea(5, 20);
        summaryTextArea.setText(selectedMatch.getSummary());
        summaryTextArea.setLineWrap(true);
        summaryTextArea.setWrapStyleWord(true);
        JScrollPane summaryScrollPane = new JScrollPane(summaryTextArea);
        c.gridwidth = GridBagConstraints.REMAINDER;
        gridBag.setConstraints(summaryScrollPane, c);
        formPanel.add(summaryScrollPane);


        this.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            Team teamRedComboBoxText = (Team) teamRedComboBox.getSelectedItem();
            Team teamBlueComboBoxText = (Team) teamBlueComboBox.getSelectedItem();
            Competition competitionComboBoxText = (Competition) competitionComboBox.getSelectedItem();
            Date occurrenceDate = (Date) occurrenceDateSpinner.getValue();
            String replayLinkText = replayLinkTextField.getText();
            String summaryText = summaryTextArea.getText();
            boolean isBlueWin = winnerCheckBox.isSelected();
            if (teamRedComboBoxText == null || teamBlueComboBoxText == null || competitionComboBoxText == null || occurrenceDate == null) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (teamRedComboBoxText.equals(teamBlueComboBoxText)) {
                JOptionPane.showMessageDialog(this, "Teams cannot be the same.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.selectedMatch.setTeamBlue(teamBlueComboBoxText);
            this.selectedMatch.setTeamRed(teamRedComboBoxText);
            this.selectedMatch.setCompetition(competitionComboBoxText);
            this.selectedMatch.setOccurrenceDate(occurrenceDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            this.selectedMatch.setReplayLink(replayLinkText);
            this.selectedMatch.setBlueWin(isBlueWin);
            this.selectedMatch.setSummary(summaryText);
            try {
                this.matchController.updateMatch(this.selectedMatch);
                JOptionPane.showMessageDialog(this, "Match updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }


            panelManager.changePanel("MatchPanel");
        });
        buttonPanel.add(saveButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
