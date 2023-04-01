package org.castruu.regions.services.impl;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.castruu.regions.entities.PlayerData;
import org.castruu.regions.entities.Region;
import org.castruu.regions.repository.RegionRepository;
import org.castruu.regions.services.RegionService;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    public Region createRegion(Region region) {
        if(regionRepository.findByName(region.getName()).isPresent()) {
            throw new IllegalArgumentException("Region already exists");
        }
        return regionRepository.create(region);
    }

    @Override
    public Region updateRegion(Region region) {
        return regionRepository.update(region);
    }

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    public Region getRegionByName(String name) {
        return regionRepository.findByName(name).orElseThrow(() ->
                new NoSuchElementException(String.format("There is no region with name %s", name))
        );
    }

    @Override
    public void addPlayerToRegionWhitelist(Region region, Player player) {
        PlayerData playerData = new PlayerData();
        playerData.setName(player.getName());
        playerData.setUuid(player.getUniqueId());
        region.getWhitelist().add(playerData);
        regionRepository.update(region);
    }

}
