package businessPackage;

import dataAccess.championDataAccess.ChampionDBAccess;
import dataAccess.championDataAccess.ChampionDataAccess;
import exceptionPackage.Champion.ReadChampionException;
import modelPackage.Champion;

import java.util.ArrayList;

public class ChampionManager {

    private ArrayList<Champion> champions;
    private final ChampionDataAccess dao;
    public ChampionManager(){
        dao = new ChampionDBAccess();
    }
    public ArrayList<Champion> getAllChampions() throws ReadChampionException {
        if (champions == null)
            champions = this.dao.getAllChampions();

        return champions;
    }
}
