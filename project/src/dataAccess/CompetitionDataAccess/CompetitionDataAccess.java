package dataAccess.CompetitionDataAccess;

import exceptionPackage.Competition.ReadCompetitionException;
import modelPackage.Competition;

import java.util.ArrayList;

public interface CompetitionDataAccess {
    Competition getCompetition(String name, int year) throws ReadCompetitionException;
    ArrayList<Competition> getAllCompetitions() throws ReadCompetitionException;
}
