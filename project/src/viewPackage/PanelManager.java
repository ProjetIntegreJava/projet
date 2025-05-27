package viewPackage;

import controllerPackage.ConnectionController;
import modelPackage.Team;
import viewPackage.game.GamesPanel;
import viewPackage.game.ModificationPanel;
import viewPackage.game.NewGamePanel;
import viewPackage.ranking.RankingPanel;
import viewPackage.searches.MatchSearchPanel;
import viewPackage.searches.ParticipationSearchPanel;
import viewPackage.searches.TeamHistorySearchPanel;
import viewPackage.team.ModificationTeamPanel;
import viewPackage.team.NewTeamPanel;
import viewPackage.team.TeamPanel;
import viewPackage.thread.ViThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class PanelManager extends JPanel {
    private final JPanel left;
    public RightPanel right;
    public JPanel center;
    private final JLabel infoLabel;
    private final MainWindow mainWindow;
    private final ConnectionController connectionController;

    public PanelManager(MainWindow mainWindow) {
        super(new FlowLayout(FlowLayout.LEADING, 0, 0));
        this.mainWindow = mainWindow;

        this.connectionController = new ConnectionController();
        PanelListener listener = new PanelListener(this);
        this.addComponentListener(listener);
        this.left = this.createLeftPanel();
        this.right = this.createRightPanel();
        this.center = this.createCenterPanel();

        ViThread viThread = new ViThread(this);
        viThread.start();

        this.infoLabel = new JLabel("<html>Fields with * <br>are mandatory</html>");
        this.infoLabel.setFont(this.getFont().deriveFont(10f));

        this.add(this.left);
        this.add(this.center);
        this.add(this.right);

        this.center.setLayout(new BorderLayout());

        JPanel container = new JPanel();

        this.center.add(container, BorderLayout.CENTER);

        this.changePanel("HomePanel");

        System.out.println(this);
    }

    public JPanel createLeftPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setPreferredSize(new Dimension(100, 400));
        return panel;
    }
    public RightPanel createRightPanel() {
        RightPanel panel = new RightPanel(new FlowLayout());
        panel.setPreferredSize(new Dimension(100, 400));

        return panel;
    }
    public JPanel createCenterPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setPreferredSize(new Dimension(400, 500));

        return panel;
    }
    public void updateLeftPanel(int width, int height) {
        this.left.setPreferredSize(new Dimension(width, height));
    }

    public void updateCenterPanel(int width, int height) {
        this.center.setPreferredSize(new Dimension(width, height));
    }

    public void updateRightPanel(int width, int height) {
        this.right.setPreferredSize(new Dimension(width, height));
    }

    public void changePanel(String panelName) {
        if(connectionController.getInstance() != null || Objects.equals(panelName, "HomePanel")) {
            this.center.removeAll();
            this.left.removeAll();
            Panel destinationPanel = switch (panelName) {
                case "NewGamePanel" -> {
                    this.left.add(this.infoLabel);
                    yield new NewGamePanel(this);
                }
                case "ModificationPanel" -> {
                    this.left.add(this.infoLabel);
                    yield new ModificationPanel(this);
                }
                case "RankingPanel" -> {
                    this.left.add(this.infoLabel);
                    yield new RankingPanel(this);
                }
                case "MatchSearchPanel" -> {
                    this.left.add(this.infoLabel);
                    yield new MatchSearchPanel();
                }
                case "ParticipationSearchPanel" -> {
                    this.left.add(this.infoLabel);
                    yield new ParticipationSearchPanel();
                }
                case "TeamHistorySearchPanel" -> {
                    this.left.add(this.infoLabel);
                    yield new TeamHistorySearchPanel();
                }
                case "NewTeamPanel" -> {
                    this.left.add(this.infoLabel);
                    yield new NewTeamPanel();
                }
                case "TeamPanel" -> {
                    this.left.add(this.infoLabel);
                    yield new TeamPanel(this);
                }
                case "GamesPanel" -> new GamesPanel();
                default -> new HomePanel();
            };
            destinationPanel.enterPanel();
            this.center.add((JPanel) destinationPanel);
            this.left.validate();
            this.left.repaint();
            this.center.validate();
            this.center.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez vous connecter à la base de données d'abord.");
        }
    }
    public void changePanel(String panelName, Object data) {
        if(connectionController.getInstance() != null || Objects.equals(panelName, "HomePanel")) {
            this.center.removeAll();
            this.left.removeAll();
            Panel destinationPanel = switch (panelName) {
                case "ModificationTeamPanel" -> {
                    this.left.add(this.infoLabel);
                    yield new ModificationTeamPanel((Team) data);
                }
                default -> new HomePanel();
            };
            destinationPanel.enterPanel();
            this.center.add((JPanel) destinationPanel);
            this.left.validate();
            this.left.repaint();
            this.center.validate();
            this.center.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez vous connecter à la base de données d'abord.");
        }
    }

    private static class PanelListener extends ComponentAdapter {
        private final PanelManager frame;
        public PanelListener(PanelManager frame) {
            this.frame = frame;
        }
        @Override
        public void componentResized(ComponentEvent event) {
            JPanel panel = (JPanel) event.getSource();
            Dimension d = panel.getSize();
            if (d.width < 800) {
                d.width = 800;
            }
            if (d.height < 500) {
                d.height = 500;
            }
            int width = (d.width) / 8;
            int height = d.height;

            this.frame.updateLeftPanel(width, height);
            this.frame.updateCenterPanel(width*6, height);
            this.frame.updateRightPanel(width, height);
            this.frame.mainWindow.pack();
        }
    }
    public static class RightPanel extends JPanel {
        public BufferedImage currImage;
        public RightPanel(FlowLayout flowLayout) {
            super(flowLayout);
        }
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int width = this.getWidth();
            Image i = this.currImage.getScaledInstance(width, width, Image.SCALE_DEFAULT);
            g.drawImage(i, 0, this.getHeight()/2 - width/2, this);
        }

        public void setCurrImage(BufferedImage currImage) {
            this.currImage = currImage;
        }
    }
}