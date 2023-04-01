package org.castruu.regions.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.castruu.regions.constants.Constants;
import org.castruu.regions.constants.RegionPermissions;
import org.castruu.regions.database.cache.RegionManager;
import org.castruu.regions.entities.PlayerData;
import org.castruu.regions.entities.Region;
import org.castruu.regions.exceptions.NotAuthorizedException;
import org.castruu.regions.services.RegionService;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Consumer;

public class RegionCommand extends BukkitCommand {


    private final RegionService regionService;

    public RegionCommand(String name) {
        super(name, Constants.REGION_COMMAND_DESCRIPTION, Constants.REGION_COMMAND_USAGE, Collections.emptyList());
        this.regionService = Bukkit.getServicesManager().load(RegionService.class);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String label, @NotNull String[] args) {
        try {
            if (args.length == 0) {
                if (commandSender instanceof ConsoleCommandSender) {
                    commandSender.sendMessage("§c[SeniorRegions] This command can only be executed by a player");
                    return true;
                }
            } else if (args.length == 1) {
                if (commandSender instanceof ConsoleCommandSender) {
                    commandSender.sendMessage("§c[SeniorRegions] This command can only be executed by a player");
                    return true;
                }
                Player player = (Player) commandSender;
                if ("wand".equals(args[0])) {
                    throwExceptionIfSenderHasNoPermission(commandSender, RegionPermissions.CREATE);
                    player.getInventory().addItem(RegionManager.get().getMagicalWand());
                } else {
                    findRegionAndExecute(commandSender, args[0], (region) -> {

                    });
                }
            } else if (args.length == 2) {
                switch (args[0]) {
                    case "whitelist" -> {
                        throwExceptionIfSenderHasNoPermission(commandSender, RegionPermissions.WHITELIST);
                        findRegionAndExecute(commandSender, args[1], (region) -> {
                            Set<PlayerData> whitelist = region.getWhitelist();
                            StringBuilder builder = new StringBuilder("§e====== WHITELIST FOR REGION §b");
                            builder.append(region.getName()).append(" §e======\n");
                            whitelist.forEach(player -> builder.append("§7").append(player.getName()).append("\n"));

                            commandSender.sendMessage(builder.toString());
                        });
                    }
                    case "create" -> {
                        throwExceptionIfSenderHasNoPermission(commandSender, RegionPermissions.CREATE);
                        if (commandSender instanceof ConsoleCommandSender) {
                            commandSender.sendMessage("§c[SeniorRegions] This command can only be executed by a player");
                            return true;
                        }
                        Player player = (Player) commandSender;
                        Region region = RegionManager.get().getRegionPlayerIsCreating(player);

                        if (region == null) {
                            commandSender.sendMessage("§c[SeniorRegions] You should start creating a region before using this command");
                        } else if (region.getStart() == null) {
                            commandSender.sendMessage("§c[SeniorRegions] Your region does not have a starting point");
                        } else if (region.getEnd() == null) {
                            commandSender.sendMessage("§c[SeniorRegions] Your region does not have a starting point");
                        } else {
                            region.setName(args[1]);
                            try {
                                regionService.createRegion(region);
                                commandSender.sendMessage(String.format("§6Congratulations! You created a region named %s. Access '/region %s' to manage it", region.getName(), region.getName()));
                            } catch (IllegalArgumentException e) {
                                commandSender.sendMessage("§c[SeniorRegions] A region with this name already exists");
                            }
                        }
                    }
                }
            } else if (args.length == 3) {
                if(!("add".equals(args[0]) || "remove".equals(args[0]))) {
                    return false;
                }
                Player player = Bukkit.getPlayerExact(args[2]);
                if(player == null) {
                    commandSender.sendMessage(String.format("§c[SeniorRegions] There is no such player named %s online", args[2]));
                    return true;
                }
                PlayerData playerData = new PlayerData();
                playerData.setName(player.getName());
                playerData.setUuid(player.getUniqueId());

                findRegionAndExecute(commandSender, args[1], (region) -> {
                    switch (args[0]) {
                        case "add" -> {
                            region.getWhitelist().add(playerData);
                            commandSender.sendMessage("§6 " + playerData.getName() + " added to the region whitelist!");
                        }
                        case "remove" -> {
                            region.getWhitelist().remove(playerData);
                            commandSender.sendMessage("§6 " + playerData.getName() + " removed from the region whitelist!");
                        }
                    }
                    regionService.updateRegion(region);
                });

            } else return false;
        } catch (NotAuthorizedException e) {
            commandSender.sendMessage("§c[SeniorRegions] You don't have permission to execute this command");
        }
        return true;
    }


    private void findRegionAndExecute(CommandSender commandSender, String regionName, Consumer<Region> consumer) {
        try {
            Region region = regionService.getRegionByName(regionName);
            System.out.println(region.getId());
            consumer.accept(region);
        } catch (NoSuchElementException e) {
            commandSender.sendMessage("§c[SeniorRegions] There is no such region named " + regionName);
        }
    }

    private void throwExceptionIfSenderHasNoPermission(CommandSender commandSender, RegionPermissions regionPermissions) {
        if (!commandSender.hasPermission(regionPermissions.getPermission()))
            throw new NotAuthorizedException(regionPermissions);
    }
}
