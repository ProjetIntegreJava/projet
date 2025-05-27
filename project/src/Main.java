import dataAccess.SingletonConnection;
import dataAccess.searches.SearchDBAccess;
import dataAccess.searches.SearchDataAccess;
import modelPackage.Competition;
import viewPackage.MainWindow;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        new MainWindow("League of Legends Database");
    }
}
