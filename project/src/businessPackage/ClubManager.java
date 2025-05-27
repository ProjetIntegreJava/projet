package businessPackage;

import dataAccess.ClubDataAccess.ClubDBAccess;
import dataAccess.ClubDataAccess.ClubDataAccess;
import exceptionPackage.Club.AddClubException;
import exceptionPackage.Club.ReadClubException;
import modelPackage.Club;

import java.util.ArrayList;

public class ClubManager {
    private final ClubDataAccess dao = new ClubDBAccess();
    public ArrayList<Club> getClubs() throws ReadClubException {
        return dao.getClubs();
    }
    public void addClub(Club club) throws AddClubException {
        dao.addClub(club);
    }
}
