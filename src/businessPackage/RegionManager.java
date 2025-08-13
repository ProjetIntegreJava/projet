package businessPackage;

import dataAccess.regionDataAccess.RegionDBAccess;
import dataAccess.regionDataAccess.RegionDataAccess;
import exceptionPackage.Region.RegionDataAccessException;
import modelPackage.Region;

import java.util.ArrayList;

public class RegionManager {
    private final RegionDataAccess dao = new RegionDBAccess();
    public ArrayList<Region> getRegions() throws RegionDataAccessException {
        return dao.getRegions();
    }
}
