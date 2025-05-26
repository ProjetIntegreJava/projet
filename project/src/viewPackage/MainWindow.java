package viewPackage;

//import controllerPackage.ConnectionController;
//import dataAccessPackage.SingletonConnection;

import controllerPackage.ConnectionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
    private final JMenuBar menuBar;
    private final JMenu appMenu, profilMenu, searchMenu, statMenu;
    private final JMenuItem exit, home, inscription, profiles, playerTournaments, gameBetweenTwoDates, playerAllMatches, winrate, OpeningUsage;
    private final PanelManager panelManager;
    private final ConnectionController connectionController;
    public MainWindow(String title) {
        super(title);
        this.panelManager = new PanelManager(this);
        this.add(panelManager, BorderLayout.CENTER);
        this.connectionController = new ConnectionController();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //connectionController.closeConnection();
                System.exit(0);
            }
        });
        this.setBounds(250, 250, 800, 500);

        this.menuBar = new JMenuBar();
        this.setJMenuBar(this.menuBar);

        this.appMenu = new JMenu("Application");
        this.menuBar.add(this.appMenu);
        this.exit = new JMenuItem("Quit");
        //Because of a conflict with Java versions between us, we can't use unnamed parameters e.g.: "_"
        //For some reason, Sebastien's SDK uses the -source 19 and Julien & Terence's SDKs use -source 24
        //Even if all of us use Java 24
        this.exit.addActionListener(e -> {
            connectionController.closeConnection();
            System.exit(0);
        });
        this.appMenu.add(this.exit);

        this.home = new JMenuItem("Home");
        this.appMenu.add(this.home);
        this.home.addActionListener(e -> panelManager.changePanel("HomePanel"));
        this.profilMenu = new JMenu("Team");
        this.menuBar.add(this.profilMenu);
        this.inscription = new JMenuItem("Create a Team");
        this.profilMenu.add(this.inscription);
        this.inscription.addActionListener(e -> panelManager.changePanel("NewTeamPanel"));
        this.profiles = new JMenuItem("Teams");
        this.profilMenu.add(this.profiles);
        this.profiles.addActionListener(e -> panelManager.changePanel("Teams"));

        this.searchMenu = new JMenu("Searches");
        this.menuBar.add(this.searchMenu);
        this.playerTournaments = new JMenuItem("Get Match Data");
        this.searchMenu.add(this.playerTournaments);
        this.playerTournaments.addActionListener(e -> panelManager.changePanel("MatchSearchPanel"));
        this.gameBetweenTwoDates = new JMenuItem("Get Participation Data");
        this.searchMenu.add(this.gameBetweenTwoDates);
        this.gameBetweenTwoDates.addActionListener(e -> panelManager.changePanel("ParticipationSearchPanel"));
        this.playerAllMatches = new JMenuItem("Get Team History");
        this.searchMenu.add(this.playerAllMatches);
        this.playerAllMatches.addActionListener(e -> panelManager.changePanel("TeamHistorySearchPanel"));

        this.statMenu = new JMenu("More info");
        this.menuBar.add(this.statMenu);
        this.winrate = new JMenuItem("Ranking Teams");
        this.statMenu.add(this.winrate);
        this.winrate.addActionListener(e -> panelManager.changePanel("RankingPanel"));
        this.OpeningUsage = new JMenuItem("Akinator Usage");
        this.statMenu.add(this.OpeningUsage);
        this.OpeningUsage.addActionListener(e -> panelManager.changePanel("AkinatorUsagePanel"));

        this.pack();

        this.setVisible(true);
    }
}