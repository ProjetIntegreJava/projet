package controllerPackage;

import businessPackage.ClubManager;
import exceptionPackage.Club.AddClubException;
import exceptionPackage.Club.ReadClubException;
import modelPackage.Club;

import java.util.ArrayList;

public class ClubController {
    private final ClubManager clubManager = new ClubManager();
    public ArrayList<Club> getClubs() throws ReadClubException {
        return clubManager.getClubs();
    }
    public void addClub(Club club) throws AddClubException {
        clubManager.addClub(club);
    }
}
