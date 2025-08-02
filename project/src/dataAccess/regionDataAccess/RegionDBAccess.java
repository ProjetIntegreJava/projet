package dataAccess.regionDataAccess;

import dataAccess.SingletonConnection;
import exceptionPackage.Region.RegionDataAccessException;
import modelPackage.Region;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegionDBAccess implements RegionDataAccess {
    @Override
    public ArrayList<Region> getRegions() throws RegionDataAccessException {
        ArrayList<Region> regions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = SingletonConnection.getInstance().prepareStatement("select * from region");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Region region = new Region(name);
                regions.add(region);
            }
        } catch (SQLException e) {
            throw new RegionDataAccessException("Error accessing region data");
        }
        return regions;
    }
}
