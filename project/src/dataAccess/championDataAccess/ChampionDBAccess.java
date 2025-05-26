package dataAccess.championDataAccess;

import dataAccess.SingletonConnection;
import exceptionPackage.Champion.ReadChampionException;
import modelPackage.Champion;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChampionDBAccess implements ChampionDataAccess {
    @Override
    public ArrayList<Champion> getAllChampions() throws ReadChampionException {
        ArrayList<Champion> champions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement("SELECT * FROM champion");
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String race = resultSet.getString("race");
                Champion champion = new Champion(name, race);
                champions.add(champion);
            }
        } catch (SQLException e) {
            throw new ReadChampionException("Error reading champions from the database");
        }
        return champions;
    }
}
