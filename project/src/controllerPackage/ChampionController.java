package controllerPackage;

import businessPackage.ChampionManager;
import exceptionPackage.Champion.ReadChampionException;
import modelPackage.Champion;

import java.util.ArrayList;

public class ChampionController {
    private final ChampionManager championManager;

    public ChampionController() {
        this.championManager = new ChampionManager();
    }

    public ArrayList<Champion> getAllChampions() throws ReadChampionException {
        return this.championManager.getAllChampions();
    }
}
