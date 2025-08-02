package dataAccess.searches;

import exceptionPackage.Search.SearchDataAccessException;
import modelPackage.Competition;
import modelPackage.searches.ResultMatchData;
import modelPackage.searches.ResultParticipationData;
import modelPackage.searches.ResultTeamHistoryData;

import java.time.LocalDate;
import java.util.ArrayList;

public interface SearchDataAccess {
    ArrayList<ResultMatchData> getSearchMatchData(Competition competition, String championName) throws SearchDataAccessException;
    ArrayList<ResultParticipationData> getSearchParticipationData(int matchID) throws SearchDataAccessException;
    ArrayList<ResultTeamHistoryData> getSearchTeamHistoryData(String teamName, LocalDate beginningDate, LocalDate endingDate) throws SearchDataAccessException;
}
