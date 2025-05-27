package controllerPackage;

import businessPackage.RegionManager;
import exceptionPackage.Region.RegionDataAccessException;
import modelPackage.Region;

import java.util.ArrayList;

public class RegionController {
    private final RegionManager regionManager = new RegionManager();
    public ArrayList<Region> getRegions() throws RegionDataAccessException {
        return regionManager.getRegions();
    }
}
