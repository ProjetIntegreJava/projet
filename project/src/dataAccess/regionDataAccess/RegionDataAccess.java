package dataAccess.regionDataAccess;

import exceptionPackage.Region.RegionDataAccessException;
import modelPackage.Region;

import java.util.ArrayList;

public interface RegionDataAccess {
    ArrayList<Region> getRegions() throws RegionDataAccessException;
}
