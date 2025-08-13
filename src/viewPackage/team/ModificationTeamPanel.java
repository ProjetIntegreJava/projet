package viewPackage.team;

import controllerPackage.TeamController;
import exceptionPackage.Team.UpdateTeamException;
import modelPackage.Region;
import modelPackage.Team;
import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class ModificationTeamPanel extends JPanel implements Panel {
    private final TeamController teamController = new TeamController();
    private final PanelManager panelManager;
    private final Team selectedTeam;
    public ModificationTeamPanel(Team selectedTeam, PanelManager panelManager) {
        this.selectedTeam = selectedTeam;
        this.panelManager = panelManager;
    }
    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        formPanel.setLayout(gridbag);
        //Name, Region, Founding Date, HasBeenWorldChampion, Description, Followers
        JLabel nameLabel = new JLabel("Name: ");
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        gridbag.setConstraints(nameLabel, c);
        formPanel.add(nameLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JTextField nameField = new JTextField(this.selectedTeam.getName());
        gridbag.setConstraints(nameField, c);
        formPanel.add(nameField);

        JLabel regionLabel = new JLabel("Region: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(regionLabel, c);
        formPanel.add(regionLabel);

        c.gridwidth = GridBagConstraints.REMAINDER;
        String[] regions = {"Amériques", "Europe", "Corée du sud", "Chine", "France", "World"};
        JComboBox<String> regionComboBox = new JComboBox<>(regions);
// Sélectionne la région actuelle de l'équipe
        regionComboBox.setSelectedItem(this.selectedTeam.getRegion().getName());
        gridbag.setConstraints(regionComboBox, c);
        formPanel.add(regionComboBox);

        JLabel foundingDateLabel = new JLabel("Founding Date: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(foundingDateLabel, c);
        formPanel.add(foundingDateLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        JSpinner foundingDateSpinner = new JSpinner(new SpinnerDateModel());
        foundingDateSpinner.setEditor(new JSpinner.DateEditor(foundingDateSpinner, formatter.toPattern()));
        Date foundingDate = Date.from(this.selectedTeam.getFoundingDate().atStartOfDay(ZoneId.systemDefault()).toInstant());;
        foundingDateSpinner.setValue(foundingDate);
        gridbag.setConstraints(foundingDateSpinner, c);
        formPanel.add(foundingDateSpinner);

        JLabel hasBeenWorldChampionLabel = new JLabel("Has Been World Champion: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(hasBeenWorldChampionLabel, c);
        formPanel.add(hasBeenWorldChampionLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JCheckBox hasBeenWorldChampionCheckBox = new JCheckBox();
        hasBeenWorldChampionCheckBox.setSelected(this.selectedTeam.hasBeenWorldChampion());
        gridbag.setConstraints(hasBeenWorldChampionCheckBox, c);
        formPanel.add(hasBeenWorldChampionCheckBox);

        JLabel descriptionLabel = new JLabel("Description: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(descriptionLabel, c);
        formPanel.add(descriptionLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JTextArea descriptionArea = new JTextArea(this.selectedTeam.getDescription(), 5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        gridbag.setConstraints(descriptionScrollPane, c);
        formPanel.add(descriptionScrollPane);

        JLabel followersLabel = new JLabel("Followers: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridbag.setConstraints(followersLabel, c);
        formPanel.add(followersLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JTextField followersField = new JTextField(String.valueOf(this.selectedTeam.getNbFollowers()));
        gridbag.setConstraints(followersField, c);
        formPanel.add(followersField);
        this.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            Label regionField = null;
            String regionName = regionField.getText();
            String description = descriptionArea.getText();
            int followers;
            try {
                followers = Integer.parseInt(followersField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Followers must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean hasBeenWorldChampion = hasBeenWorldChampionCheckBox.isSelected();
            Date date = (Date) foundingDateSpinner.getValue();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            java.time.LocalDate foundingDateLocal = calendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String teamName = this.selectedTeam.getName();

            // Update the selected team
            this.selectedTeam.setName(name);
            this.selectedTeam.setRegion(new Region(regionName));
            this.selectedTeam.setFoundingDate(foundingDateLocal);
            this.selectedTeam.setHasBeenWorldChampion(hasBeenWorldChampion);
            this.selectedTeam.setDescription(description);
            this.selectedTeam.setNbFollower(followers);

            try {
                this.teamController.updateTeam(teamName, this.selectedTeam);
            } catch (UpdateTeamException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            panelManager.changePanel("TeamPanel");
        });
        buttonPanel.add(saveButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
