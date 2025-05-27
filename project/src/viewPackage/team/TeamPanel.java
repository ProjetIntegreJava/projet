package viewPackage.team;

import controllerPackage.TeamController;
import exceptionPackage.Team.ReadTeamException;
import modelPackage.Team;
import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TeamPanel extends JPanel implements Panel {
    private final PanelManager panelManager;
    private final TeamController teamController = new TeamController();
    private ArrayList<Team> allTeams = new ArrayList<>();

    public TeamPanel(PanelManager panelManager) {
        this.panelManager = panelManager;
    }
    private JTable teamTable;
    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
        JPanel tablePanel = new JPanel(new BorderLayout());

        try {
            this.allTeams = teamController.getAllTeams();
        } catch (ReadTeamException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.teamTable = new JTable();
        tablePanel.add(new JScrollPane(teamTable));
        String[] columnNames = {"Name", "Club Name", "Region", "Founding Date", "Has Been World Champion", "Description", "Followers"};
        Object[][] data = new Object[this.allTeams.size()][columnNames.length];
        for (int i = 0; i < this.allTeams.size(); i++) {
            Team team = this.allTeams.get(i);
            data[i][0] = team.getName();
            data[i][1] = team.getClub().getName();
            data[i][2] = team.getRegion().getName();
            data[i][3] = team.getFoundingDate().toString();
            data[i][4] = team.hasBeenWorldChampion() ? "Yes" : "No";
            data[i][5] = team.getDescription();
            data[i][6] = team.getNbFollowers();
        }
        teamTable.setModel(new DefaultTableModel(data, columnNames));

        this.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            this.panelManager.changePanel("NewTeamPanel");
        });
        JButton modifyButton = new JButton("Modify");
        ArrayList<Team> finalTeams = this.allTeams;
        modifyButton.addActionListener(e -> {
            if (teamTable.getSelectedRow() != -1) {
                int selectedRow = teamTable.getSelectedRow();
                Team selectedTeam = finalTeams.get(selectedRow);
                this.panelManager.changePanel("ModificationTeamPanel", selectedTeam);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a team to modify.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        this.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
    }
}
