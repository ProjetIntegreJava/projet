package dataAccess.championDataAccess;

import exceptionPackage.Champion.ReadChampionException;
import modelPackage.Champion;

import java.util.ArrayList;

public interface ChampionDataAccess {
    ArrayList<Champion> getAllChampions() throws ReadChampionException;
    Champion getChampionByName(String nameChampion) throws ReadChampionException;
}
