package viewPackage.match;

import controllerPackage.MatchController;
import exceptionPackage.Match.ReadMatchException;
import modelPackage.Match;
import viewPackage.Panel;
import viewPackage.PanelManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.ZoneId;
import java.util.ArrayList;

public class MatchPanel extends JPanel implements Panel {
    private final PanelManager panelManager;
    private final MatchController matchController = new MatchController();
    private ArrayList<Match> allMatches = new ArrayList<>();

    public MatchPanel(PanelManager panelManager) {
        this.panelManager = panelManager;
    }
    private JTable matchTable;
    @Override
    public void enterPanel() {
        this.setLayout(new BorderLayout());
        JPanel tablePanel = new JPanel(new BorderLayout());

        try {
            this.allMatches = matchController.getAllMatches();
        } catch (ReadMatchException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        this.matchTable = new JTable();
        tablePanel.add(new JScrollPane(matchTable));
        String[] columnNames = {"ID", "Team Blue Name", "Team Red Name", "Competition Name", "Occurrence Date", "Blue wins", "Replay URL", "Summary"};
        Object[][] data = new Object[this.allMatches.size()][columnNames.length];
        for (int i = 0; i < this.allMatches.size(); i++) {
            Match match = this.allMatches.get(i);
            data[i][0] = match.getId();
            data[i][1] = match.getTeamBlue().getName();
            data[i][2] = match.getTeamRed().getName();
            data[i][3] = match.getCompetition().getName();
            data[i][4] = match.getOccurrenceDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
            data[i][5] = match.isBlueWin();
            data[i][6] = match.getReplayLink() != null ? match.getReplayLink() : "N/A";
            data[i][7] = match.getSummary() != null ? match.getSummary() : "N/A";
        }
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 5) return Boolean.class;
                return super.getColumnClass(column);
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        matchTable.setModel(model);


        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        matchTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);

        this.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            this.panelManager.changePanel("NewMatchPanel");
        });
        JButton modifyButton = new JButton("Modify");
        ArrayList<Match> finalMatches = this.allMatches;
        modifyButton.addActionListener(e -> {
            if (matchTable.getSelectedRow() != -1) {
                int selectedRow = matchTable.getSelectedRow();
                Match selectedMatch = finalMatches.get(selectedRow);
                this.panelManager.changePanel("ModificationMatchPanel", selectedMatch);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a match to modify.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            if (matchTable.getSelectedRows() != null && matchTable.getSelectedRow() != -1) {
                Match selectedMatch = finalMatches.get(matchTable.getSelectedRow());
                System.out.println("Selected team to delete: " + selectedMatch);
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete: [" + selectedMatch + "]?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        matchController.deleteMatch(selectedMatch.getId());
                        JOptionPane.showMessageDialog(this, "Match deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        this.panelManager.changePanel("MatchPanel");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a match to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        this.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
    }
}