package org.castruu.regions.listeners;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
import java.util.function.Function;

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
        if (!player.hasPermission(RegionPermissions.CREATE.getPermission())) {
            player.sendMessage("§cYou don't have permission to create regions!");
            return;
        }

        Action action = event.getAction();
        Region playerRegion = RegionManager.get().getRegionPlayerIsCreating(player);
        Location location = event.getClickedBlock().getLocation();

        final Function<Location, String> formattedLocation = (l) -> String.format("x: %s, y: %s, z:%s", l.getX(), l.getY(), l.getZ());
        switch (action) {
            case RIGHT_CLICK_BLOCK -> {
                playerRegion.setStart(location);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aThe region start is set to " + formattedLocation.apply(location)));
            }
            case LEFT_CLICK_BLOCK -> {
                playerRegion.setEnd(location);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§aThe region end is set to " + formattedLocation.apply(location)));
            }
            case LEFT_CLICK_AIR -> {
                if (playerRegion != null)
                    player.sendMessage("§6Information about current region -> \n" +
                            "§7Start: " + formattedLocation.apply(playerRegion.getStart()) + "\n" +
                            "§7End: " + formattedLocation.apply(playerRegion.getEnd()));
            }
        }
    }


}

