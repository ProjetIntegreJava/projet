package dataAccess.PlayerDataAccess;

import exceptionPackage.Player.ReadPlayerException;
import modelPackage.Player;

public interface PlayerDataAccess {
    Player getPlayerById(int playerId) throws ReadPlayerException;
}
