package controllerPackage;

import businessPackage.CompetitionManager;
import exceptionPackage.Competition.ReadCompetitionException;
import modelPackage.Competition;

import java.util.ArrayList;

public class CompetitionController {
    private final CompetitionManager competitionManager = new CompetitionManager();

    public ArrayList<Competition> getAllCompetitions() throws ReadCompetitionException {
        return competitionManager.getAllCompetitions();
    }
}
