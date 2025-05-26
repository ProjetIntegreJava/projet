import dataAccess.SingletonConnection;
import dataAccess.searches.ResearchDBAccess;
import dataAccess.searches.ResearchDataAccess;
import modelPackage.Competition;
import viewPackage.MainWindow;

public class Main {
    public static void main(String[] args) {
        ResearchDataAccess researchDataAccess = new ResearchDBAccess();
        try {
            SingletonConnection.databaseLogin("Gipson62#8015");
            var res = researchDataAccess.getSearchMatchData(new Competition("World Championship", 2023), "Vi");
            System.out.println(res);
            var res2 = researchDataAccess.getSearchParticipationData(10);
            System.out.println(res2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainWindow mainWindow = new MainWindow("League of Legends Database");
    }
}
