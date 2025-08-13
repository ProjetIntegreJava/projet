package controllerPackage;

import businessPackage.SearchManager;
import exceptionPackage.Search.SearchDataAccessException;
import modelPackage.Competition;
import modelPackage.searches.ResultMatchData;
import modelPackage.searches.ResultParticipationData;
import modelPackage.searches.ResultTeamHistoryData;

import java.time.LocalDate;
import java.util.ArrayList;

public class SearchController {
    private final SearchManager searchManager = new SearchManager();
    public ArrayList<ResultMatchData> getSearchMatchData(Competition competition, String championName) throws SearchDataAccessException {
        return searchManager.getSearchMatchData(competition, championName);
    }
    public ArrayList<ResultParticipationData> getSearchParticipationData(int matchID) throws SearchDataAccessException {
        return searchManager.getSearchParticipationData(matchID);
    }
    public ArrayList<ResultTeamHistoryData> getSearchTeamHistoryData(String teamName, LocalDate beginningDate, LocalDate endingDate) throws SearchDataAccessException {
        return searchManager.getSearchTeamHistoryData(teamName, beginningDate, endingDate);
    }
}
