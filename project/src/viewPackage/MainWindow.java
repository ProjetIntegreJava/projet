package viewPackage;

//import controllerPackage.ConnectionController;
//import dataAccessPackage.SingletonConnection;

import controllerPackage.ConnectionController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainWindow extends JFrame {
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
                connectionController.closeConnection();
                System.exit(0);
            }
        });
        this.setBounds(250, 250, 800, 500);

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu appMenu = new JMenu("Application");
        menuBar.add(appMenu);
        JMenuItem exit = new JMenuItem("Quit");
        //Because of a conflict with Java versions between us, we can't use unnamed parameters e.g.: "_"
        //For some reason, Sebastien's SDK uses the -source 19 and Julien & Terence's SDKs use -source 24
        //Even if all of us use Java 24
        exit.addActionListener(e -> {
            connectionController.closeConnection();
            System.exit(0);
        });
        appMenu.add(exit);

        JMenuItem home = new JMenuItem("Home");
        appMenu.add(home);
        home.addActionListener(e -> panelManager.changePanel("HomePanel"));
        JMenu teamMenu = new JMenu("Team");
        menuBar.add(teamMenu);
        JMenuItem createTeamMenu = new JMenuItem("Create a Team");
        teamMenu.add(createTeamMenu);
        createTeamMenu.addActionListener(e -> panelManager.changePanel("NewTeamPanel"));
        JMenuItem teamsMenuItem = new JMenuItem("Teams");
        teamMenu.add(teamsMenuItem);
        teamsMenuItem.addActionListener(e -> panelManager.changePanel("TeamPanel"));

        JMenu searchMenu = new JMenu("Searches");
        menuBar.add(searchMenu);
        JMenuItem matchDataMenuItem = new JMenuItem("Get Match Data");
        searchMenu.add(matchDataMenuItem);
        matchDataMenuItem.addActionListener(e -> panelManager.changePanel("MatchSearchPanel"));
        JMenuItem participationDataMenuItem = new JMenuItem("Get Participation Data");
        searchMenu.add(participationDataMenuItem);
        participationDataMenuItem.addActionListener(e -> panelManager.changePanel("ParticipationSearchPanel"));
        JMenuItem teamHistoryMenuItem = new JMenuItem("Get Team History");
        searchMenu.add(teamHistoryMenuItem);
        teamHistoryMenuItem.addActionListener(e -> panelManager.changePanel("TeamHistorySearchPanel"));

        JMenu infoMenu = new JMenu("More info");
        menuBar.add(infoMenu);
        JMenuItem rankingMenuItem = new JMenuItem("Ranking Teams");
        infoMenu.add(rankingMenuItem);
        rankingMenuItem.addActionListener(e -> panelManager.changePanel("PlayerRankingForMatchPanel"));
        JMenuItem akinatorMenuItem = new JMenuItem("Akinator Usage");
        infoMenu.add(akinatorMenuItem);
        akinatorMenuItem.addActionListener(e -> panelManager.changePanel("AkinatorUsagePanel"));

        this.pack();

        this.setVisible(true);
    }
}