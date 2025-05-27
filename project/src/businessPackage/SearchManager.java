package businessPackage;

import dataAccess.searches.SearchDBAccess;
import dataAccess.searches.SearchDataAccess;
import exceptionPackage.Search.SearchDataAccessException;
import modelPackage.Competition;
import modelPackage.searches.ResultMatchData;
import modelPackage.searches.ResultParticipationData;
import modelPackage.searches.ResultTeamHistoryData;

import java.time.LocalDate;
import java.util.ArrayList;

public class SearchManager {
    private final SearchDataAccess dao = new SearchDBAccess();
    public ArrayList<ResultMatchData> getSearchMatchData(Competition competition, String championName) throws SearchDataAccessException {
        return dao.getSearchMatchData(competition, championName);
    }
    public ArrayList<ResultParticipationData> getSearchParticipationData(int matchID) throws SearchDataAccessException {
        return dao.getSearchParticipationData(matchID);
    }
    public ArrayList<ResultTeamHistoryData> getSearchTeamHistoryData(String teamName, LocalDate beginningDate, LocalDate endingDate) throws SearchDataAccessException {
        return dao.getSearchTeamHistoryData(teamName, beginningDate, endingDate);
    }
}
