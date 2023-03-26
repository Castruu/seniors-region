package org.castruu.regions.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.castruu.regions.constants.RegionPermissions;
import org.castruu.regions.database.cache.RegionManager;
import org.castruu.regions.entities.Region;

import java.util.Objects;

public class WandInteractEventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void wandInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getItem() == null) return;
        if (!Objects.equals(event.getItem(), RegionManager.get().getMagicalWand())) {
            return;
        }
        event.setCancelled(true);
        Player player = event.getPlayer();
        if (player.hasPermission(RegionPermissions.CREATE.getPermission())) {
            player.sendMessage("§cYou don't have permission to create regions!");
            return;
        }

        Action action = event.getAction();
        Region playerRegion = RegionManager.get().getRegionPlayerIsCreating(player);
        Location location = event.getClickedBlock().getLocation();

        switch (action) {
            case RIGHT_CLICK_BLOCK:
                playerRegion.setStart(location);
                player.sendMessage(String.format(
                        "§aThe region start is set to x: %s, y: %s, z:%s",
                        location.getX(), location.getY(), location.getZ()
                ));
                return;
            case LEFT_CLICK_BLOCK:
                playerRegion.setEnd(location);
                player.sendMessage(String.format(
                        "§aThe region end is set to x: %s, y: %s, z:%s",
                        location.getX(), location.getY(), location.getZ()
                ));
        }
    }


}

