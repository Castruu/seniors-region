package org.castruu.regions.services;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.castruu.regions.entities.Region;

public interface RegionService {

    Region getRegionFromLocation(Location location);

    boolean isInRegion(Region region, Location location);

}
