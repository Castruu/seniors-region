package org.castruu.regions.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.castruu.regions.constants.Constants;
import org.castruu.regions.database.cache.RegionManager;
import org.castruu.regions.entities.PlayerData;
import org.castruu.regions.entities.Region;
import org.castruu.regions.services.RegionService;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class RegionCommand extends BukkitCommand {


    private final RegionService regionService;

    public RegionCommand(String name) {
        super(name, Constants.REGION_COMMAND_DESCRIPTION, Constants.REGION_COMMAND_USAGE, Collections.emptyList());
        this.regionService = Bukkit.getServicesManager().load(RegionService.class);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {

        } else if (args.length == 1) {
            if (commandSender instanceof ConsoleCommandSender) {
                commandSender.sendMessage("§c[SeniorRegions] This command can only be executed by a player");
                return true;
            }
            Player player = (Player) commandSender;
            if ("wand".equals(args[0])) {
                player.getInventory().addItem(RegionManager.get().getMagicalWand());
            } else {
                findRegionAndExecute(commandSender, args[0], (region) -> {

                });
            }
        } else if (args.length == 2) {
            if ("whitelist".equals(args[0])) {
                findRegionAndExecute(commandSender, args[1], (region) -> {
                    List<PlayerData> whitelist = region.getWhitelist();
                    StringBuilder builder = new StringBuilder("§d====== &6WHITELIST FOR REGION ");
                    builder.append(region.getName()).append(" §d======\n");
                    whitelist.forEach(player -> builder.append(player.getName()).append("\n"));

                    commandSender.sendMessage(builder.toString());
                });
            }
        } else if (args.length == 3) {

        } else return false;
        return true;
    }


    private void findRegionAndExecute(CommandSender commandSender, String regionName, Consumer<Region> consumer) {
        try {
            Region region = regionService.getRegionByName(regionName);
            consumer.accept(region);
        } catch (NoSuchElementException e) {
            commandSender.sendMessage("§c[SeniorRegions] There is no such region named " + regionName);
        }
    }


}
