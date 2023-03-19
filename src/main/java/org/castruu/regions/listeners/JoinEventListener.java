package org.castruu.regions.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.castruu.regions.entities.Region;
import org.castruu.regions.repository.RegionRepository;

import java.util.List;
import java.util.UUID;

public class JoinEventListener implements Listener {

    private final RegionRepository regionRepository;

    public JoinEventListener() {
        this.regionRepository = Bukkit.getServicesManager().load(RegionRepository.class);
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        System.out.println("PLAYER JOINED");
        org.bukkit.Location location = e.getPlayer().getLocation();
        Region region = new Region(UUID.randomUUID(), location);

        regionRepository.create(region);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        List<Region> regions = regionRepository.findAll();

        regions.forEach(it -> {
            System.out.println(it.getUuid());
            System.out.println(it.getLocation().getWorld().getUID());
        });

    }

}
