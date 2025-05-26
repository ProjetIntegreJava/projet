import dataAccess.SingletonConnection;
import dataAccess.searches.SearchDBAccess;
import dataAccess.searches.SearchDataAccess;
import modelPackage.Competition;
import viewPackage.MainWindow;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        SearchDataAccess researchDataAccess = new SearchDBAccess();
        try {
            SingletonConnection.databaseLogin("Gipson62#8015");
            var res = researchDataAccess.getSearchMatchData(new Competition("World Championship", 2023), "Vi");
            System.out.println(res);
            var res2 = researchDataAccess.getSearchParticipationData(10);
            System.out.println(res2);
            var res3 = researchDataAccess.getSearchTeamHistoryData("Fnatic", LocalDate.of(2023, 1, 1), LocalDate.now());
            System.out.println(res3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainWindow mainWindow = new MainWindow("League of Legends Database");
    }
}
