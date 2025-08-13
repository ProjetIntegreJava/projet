package viewPackage.match;

import controllerPackage.CompetitionController;
import controllerPackage.MatchController;
import controllerPackage.TeamController;
import exceptionPackage.Match.AddMatchException;
import modelPackage.Competition;
import modelPackage.Match;
import modelPackage.Region;
import modelPackage.Team;
import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class NewMatchPanel extends JPanel implements Panel {
    private final MatchController matchController = new MatchController();
    private final TeamController teamController = new TeamController();
    private final CompetitionController competitionController = new CompetitionController();
    private final PanelManager panelManager;
    public NewMatchPanel(PanelManager panelManager) {
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

        JLabel teamBlueNameLabel = new JLabel("Team Blue Name*: ");
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        gridBag.setConstraints(teamBlueNameLabel, c);
        formPanel.add(teamBlueNameLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JComboBox<Team> teamBlueComboBox = new JComboBox<>(teams.toArray(new Team[0]));
        gridBag.setConstraints(teamBlueComboBox, c);
        formPanel.add(teamBlueComboBox);

        JLabel teamRedNameLabel = new JLabel("Team Red Name*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(teamRedNameLabel, c);
        formPanel.add(teamRedNameLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JComboBox<Team> teamRedComboBox = new JComboBox<>(teams.toArray(new Team[0]));
        gridBag.setConstraints(teamRedComboBox, c);
        formPanel.add(teamRedComboBox);

        JLabel competitionNameLabel = new JLabel("Competition*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(competitionNameLabel, c);
        formPanel.add(competitionNameLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JComboBox<Competition> competitionComboBox = new JComboBox<>(competitions.toArray(new Competition[0]));
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
        gridBag.setConstraints(occurrenceDateSpinner, c);
        formPanel.add(occurrenceDateSpinner);

        JLabel replayLinkLabel = new JLabel("Replay Link: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(replayLinkLabel, c);
        formPanel.add(replayLinkLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JTextField replayLinkTextField = new JTextField(20);
        gridBag.setConstraints(replayLinkTextField, c);
        formPanel.add(replayLinkTextField);

        JLabel winnerLabel = new JLabel("Did Blue win?*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(winnerLabel, c);
        formPanel.add(winnerLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JCheckBox winnerCheckBox = new JCheckBox();
        gridBag.setConstraints(winnerCheckBox, c);
        formPanel.add(winnerCheckBox);

        JLabel summaryLabel = new JLabel("Match Summary: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(summaryLabel, c);
        formPanel.add(summaryLabel);
        JTextArea summaryTextArea = new JTextArea(5, 20);
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
            Team teamRed = (Team) teamRedComboBox.getSelectedItem();
            Team teamBlue = (Team) teamBlueComboBox.getSelectedItem();
            Competition competitionComboBoxText = (Competition) competitionComboBox.getSelectedItem();
            Date occurrenceDate = (Date) occurrenceDateSpinner.getValue();
            String replayLinkText = replayLinkTextField.getText();
            String summaryText = summaryTextArea.getText();
            boolean isBlueWin = winnerCheckBox.isSelected();
            if (teamRed == null || teamBlue == null || competitionComboBoxText == null || occurrenceDate == null) {
                JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (teamRed.equals(teamBlue)) {
                JOptionPane.showMessageDialog(this, "Teams cannot be the same.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                matchController.addMatch(new Match(
                        teamBlue,
                        teamRed,
                        competitionComboBoxText,
                        LocalDate.now(),
                        occurrenceDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        isBlueWin,
                        replayLinkText,
                        summaryText
                ));
            } catch (AddMatchException ex) {
                JOptionPane.showMessageDialog(this, "Error adding match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            panelManager.changePanel("MatchPanel");
        });
        buttonPanel.add(saveButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
