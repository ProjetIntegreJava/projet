package dataAccess.ClubDataAccess;

import exceptionPackage.Club.*;
import modelPackage.Club;

public interface ClubDataAccess {
    Club getClub(String nameClub) throws ReadClubException;
}
