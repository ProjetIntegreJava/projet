package dataAccess.CompetitionDataAccess;

import exceptionPackage.Competition.ReadCompetitionException;
import modelPackage.Competition;

public interface CompetitionDataAccess {
    Competition getCompetition(String name, int year) throws ReadCompetitionException;
}
