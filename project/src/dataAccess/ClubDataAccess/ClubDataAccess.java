package dataAccess.ClubDataAccess;

import exceptionPackage.Club.*;
import modelPackage.Club;

import java.util.ArrayList;

public interface ClubDataAccess {
    Club getClub(String nameClub) throws ReadClubException;
    ArrayList<Club> getClubs() throws ReadClubException;
    void addClub(Club club) throws AddClubException;
}
