package org.castruu.regions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.castruu.regions.database.MongoDatabaseProvider;
import org.castruu.regions.listeners.InteractEventListener;
import org.castruu.regions.listeners.WandInteractEventListener;
import org.castruu.regions.repository.RegionRepository;
import org.castruu.regions.services.RegionService;
import org.castruu.regions.services.impl.RegionServiceImpl;

import java.lang.reflect.Field;

public final class SeniorRegions extends JavaPlugin {

    private ServicesManager servicesManager;
    private MongoDatabaseProvider mongoDatabaseProvider;
    private RegionRepository regionRepository;

    @Override
    public void onEnable() {
        servicesManager = Bukkit.getServicesManager();
        mongoDatabaseProvider = new MongoDatabaseProvider(this);
        mongoDatabaseProvider.connect();

        regionRepository = new RegionRepository(mongoDatabaseProvider);
        RegionService regionService = new RegionServiceImpl(regionRepository);

        servicesManager.register(RegionService.class, regionService, this, ServicePriority.High);

        Bukkit.getPluginManager().registerEvents(new InteractEventListener(), this);
        Bukkit.getPluginManager().registerEvents(new WandInteractEventListener(), this);
    }

    @Override
    public void onDisable() {
        mongoDatabaseProvider.close();
    }

    public void setCommand(String name, Command command) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);

            final CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(name, command);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[SeniorRegions]: An error happened while registering commands, stopping plugin.");
            this.getPluginLoader().disablePlugin(this);
        }
    }

}
