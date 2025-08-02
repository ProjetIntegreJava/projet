package businessPackage;

import dataAccess.CompetitionDataAccess.CompetitionDBAccess;
import dataAccess.CompetitionDataAccess.CompetitionDataAccess;
import exceptionPackage.Competition.ReadCompetitionException;
import modelPackage.Competition;

import java.util.ArrayList;

public class CompetitionManager {
    private final CompetitionDataAccess dao = new CompetitionDBAccess();
    private ArrayList<Competition> competitions;

    public ArrayList<Competition> getAllCompetitions() throws ReadCompetitionException {
        if (competitions == null)
            competitions = dao.getAllCompetitions();
        return competitions;
    }
}
