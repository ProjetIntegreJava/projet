package viewPackage.team;

import controllerPackage.ClubController;
import controllerPackage.RegionController;
import controllerPackage.TeamController;
import exceptionPackage.Team.AddTeamException;
import exceptionPackage.Team.UpdateTeamException;
import modelPackage.Club;
import modelPackage.Region;
import modelPackage.Team;
import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NewTeamPanel extends JPanel implements Panel {
    private final TeamController teamController = new TeamController();
    private final PanelManager panelManager;
    private final ClubController clubController = new ClubController();
    private final RegionController regionController = new RegionController();
    private final Boolean clubAlreadyExists;
    private JTextField clubField, nationalityField, ceoField;
    private JComboBox<Club> teamComboBox;
    public NewTeamPanel(PanelManager panelManager, Boolean clubAlreadyExists) {
        this.panelManager = panelManager;
        this.clubAlreadyExists = clubAlreadyExists;
    }
    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        formPanel.setLayout(gridBag);

        JLabel nameLabel = new JLabel("Name*: ");
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        gridBag.setConstraints(nameLabel, c);
        formPanel.add(nameLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JTextField nameField = new JTextField("wii");
        gridBag.setConstraints(nameField, c);
        formPanel.add(nameField);

        ArrayList<Region> regions = new ArrayList<>();
        try {
            regions = this.regionController.getRegions();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        JLabel regionLabel = new JLabel("Region*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(regionLabel, c);
        formPanel.add(regionLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JComboBox<Region> regionJComboBox = new JComboBox<>(regions.toArray(new Region[0]));
        gridBag.setConstraints(regionJComboBox, c);
        formPanel.add(regionJComboBox);

        JLabel foundingDateLabel = new JLabel("Founding Date*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(foundingDateLabel, c);
        formPanel.add(foundingDateLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        JSpinner foundingDateSpinner = new JSpinner(new SpinnerDateModel());
        foundingDateSpinner.setEditor(new JSpinner.DateEditor(foundingDateSpinner, formatter.toPattern()));
        Date foundingDate = Date.from(java.time.LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        foundingDateSpinner.setValue(foundingDate);
        gridBag.setConstraints(foundingDateSpinner, c);
        formPanel.add(foundingDateSpinner);

        JLabel hasBeenWorldChampionLabel = new JLabel("Has Been World Champion*: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(hasBeenWorldChampionLabel, c);
        formPanel.add(hasBeenWorldChampionLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JCheckBox hasBeenWorldChampionCheckBox = new JCheckBox();
        hasBeenWorldChampionCheckBox.setSelected(false);
        gridBag.setConstraints(hasBeenWorldChampionCheckBox, c);
        formPanel.add(hasBeenWorldChampionCheckBox);

        JLabel descriptionLabel = new JLabel("Description: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(descriptionLabel, c);
        formPanel.add(descriptionLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JTextArea descriptionArea = new JTextArea("cool");
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        gridBag.setConstraints(descriptionScrollPane, c);
        formPanel.add(descriptionScrollPane);

        JLabel followersLabel = new JLabel("Followers: ");
        c.gridwidth = GridBagConstraints.RELATIVE;
        gridBag.setConstraints(followersLabel, c);
        formPanel.add(followersLabel);
        c.gridwidth = GridBagConstraints.REMAINDER;
        JTextField followersField = new JTextField();
        gridBag.setConstraints(followersField, c);
        formPanel.add(followersField);
        this.add(formPanel, BorderLayout.NORTH);

        if (!this.clubAlreadyExists) {
            JLabel clubAlreadyExistsLabel = new JLabel("Club*:");
            c.gridwidth = GridBagConstraints.RELATIVE;
            gridBag.setConstraints(clubAlreadyExistsLabel, c);
            formPanel.add(clubAlreadyExistsLabel);
            c.gridwidth = GridBagConstraints.REMAINDER;
            this.clubField = new JTextField();
            gridBag.setConstraints(this.clubField, c);
            formPanel.add(this.clubField);

            JLabel nationalityLabel = new JLabel("Nationality*: ");
            c.gridwidth = GridBagConstraints.RELATIVE;
            gridBag.setConstraints(nationalityLabel, c);
            formPanel.add(nationalityLabel);
            c.gridwidth = GridBagConstraints.REMAINDER;
            this.nationalityField = new JTextField();
            gridBag.setConstraints(this.nationalityField, c);
            formPanel.add(this.nationalityField);

            JLabel ceoLabel = new JLabel("CEO*: ");
            c.gridwidth = GridBagConstraints.RELATIVE;
            gridBag.setConstraints(ceoLabel, c);
            formPanel.add(ceoLabel);
            c.gridwidth = GridBagConstraints.REMAINDER;
            this.ceoField = new JTextField();
            gridBag.setConstraints(this.ceoField, c);
            formPanel.add(this.ceoField);
        } else {
            ArrayList<Club> clubs = new ArrayList<>();
            try {
                clubs = this.clubController.getClubs();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            JLabel clubLabel = new JLabel("Club*: ");
            c.gridwidth = GridBagConstraints.RELATIVE;
            gridBag.setConstraints(clubLabel, c);
            formPanel.add(clubLabel);
            c.gridwidth = GridBagConstraints.REMAINDER;
            this.teamComboBox = new JComboBox<>(clubs.toArray(new Club[0]));
            gridBag.setConstraints(this.teamComboBox, c);
            formPanel.add(this.teamComboBox);
        }

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Region selectedRegion = (Region) regionJComboBox.getSelectedItem();
            if (selectedRegion == null) {
                JOptionPane.showMessageDialog(this, "Please select a region.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Club club;
            if (this.clubAlreadyExists) {
                Club selectedClub = (Club) this.teamComboBox.getSelectedItem();
                if (selectedClub == null) {
                    JOptionPane.showMessageDialog(this, "Please select a club.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                club = selectedClub;
            } else {
                String clubName = this.clubField.getText();
                String nationality = this.nationalityField.getText();
                String ceo = this.ceoField.getText();
                if (clubName.isEmpty() || nationality.isEmpty() || ceo.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Club data cannot be empty (nationality, ceo, club name).", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                club = new Club(clubName, ceo, nationality);
            }
            String regionName = regionJComboBox.getSelectedItem().toString();
            if (regionName == null || regionName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Region cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String description = descriptionArea.getText();
            if (description.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Description cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Integer followers = null; // facultatif

            String txt = followersField.getText().trim();

            if (!txt.isEmpty()) { // seulement si l'utilisateur a saisi qqch
                try {
                    followers = Integer.valueOf(txt);
                    if (followers < 0) {
                        JOptionPane.showMessageDialog(this, "Followers cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Followers must be a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
// ici tu utilises followers (peut être null si rien saisi)

            boolean hasBeenWorldChampion = hasBeenWorldChampionCheckBox.isSelected();
            Date date = (Date) foundingDateSpinner.getValue();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            java.time.LocalDate foundingDateLocal = calendar.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Team newTeam = new Team(name, club, selectedRegion, null, foundingDateLocal, hasBeenWorldChampion, description, followers);

            try {
                if (this.clubAlreadyExists) {
                    this.teamController.addTeam(newTeam);
                } else {
                    this.clubController.addClub(club);
                    this.teamController.addTeam(newTeam);
                }
            } catch (AddTeamException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.panelManager.changePanel("TeamPanel");
        });
        buttonPanel.add(saveButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}