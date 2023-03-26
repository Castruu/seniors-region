package org.castruu.regions.services.impl;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.castruu.regions.entities.Region;
import org.castruu.regions.repository.RegionRepository;
import org.castruu.regions.services.RegionService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }


    @Override
        public Region getRegionFromLocation(Location location) {
            List<Region> regions = regionRepository.findAll();
            return regions.stream()
                    .filter(it -> isInRegion(it, location))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isInRegion(Region region, Location location) {
        double playerFromLoc1 = location.distanceSquared(region.getStart());
        double playerFromLoc2 = location.distanceSquared(region.getEnd());
        double distanceSquaredBetween = region.getStart().distanceSquared(region.getEnd());
        return playerFromLoc1 + playerFromLoc2 == distanceSquaredBetween;
    }

}
