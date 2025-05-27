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
            String[] options = new String[]{"Yes", "No"};
            JPanel panel = new JPanel(new BorderLayout());
            JLabel infoLabel = new JLabel("Does the team club already exists?");
            panel.add(infoLabel);
            int option = JOptionPane.showOptionDialog(null, panel,"Wait", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
            if (option == 0) {
                this.panelManager.changePanel("NewTeamPanel", true);
            } else if (option == 1) {
                this.panelManager.changePanel("NewTeamPanel", false);
            }
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
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            if (teamTable.getSelectedRows() != null && teamTable.getSelectedRow() != -1) {
                String selectedTeam = teamTable.getValueAt(teamTable.getSelectedRow(), 0).toString();
                System.out.println("Selected team to delete: " + selectedTeam);
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete: [" + selectedTeam + "]?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        teamController.deleteTeam(selectedTeam);
                        JOptionPane.showMessageDialog(this, "Team deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.panelManager.changePanel("TeamPanel");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a team to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
    }
}
