package org.castruu.regions.services;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.castruu.regions.entities.Region;

import java.util.List;

public interface RegionService {

    Region getRegionFromLocation(Location location);

    boolean isInRegion(Region region, Location location);

    List<Region> getAllRegions();

    Region getRegionByName(String name);

    void addPlayerToRegionWhitelist(Region region, Player player);
}
