package org.castruu.regions.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.castruu.regions.constants.RegionPermissions;
import org.castruu.regions.entities.PlayerData;
import org.castruu.regions.entities.Region;
import org.castruu.regions.services.RegionService;

import java.util.List;

public class InteractEventListener implements Listener {

    private final RegionService regionService;

    public InteractEventListener() {
        this.regionService = Bukkit.getServicesManager().load(RegionService.class);
    }


    @EventHandler
    public void interact(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        Player player = event.getPlayer();
        if (player.hasPermission(RegionPermissions.BYPASS.getPermission())) return;
        Region region = regionService.getRegionFromLocation(player.getLocation());
        if(region == null) return;
        List<PlayerData> whitelist = region.getWhitelist();
        boolean isPlayerWhitelisted = whitelist
                .stream()
                .anyMatch(it -> it.getUuid().equals(player.getUniqueId()));
        if(!isPlayerWhitelisted) {
            event.setCancelled(true);
            player.sendMessage(String.format(
                    "%sThis region belongs to %s, you don't have permission to interact",
                    ChatColor.RED, region.getOwner().getName()
            ));
        }
    }


}
