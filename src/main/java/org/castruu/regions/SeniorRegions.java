package org.castruu.regions;

import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.castruu.regions.database.MongoDatabaseProvider;
import org.castruu.regions.listeners.JoinEventListener;
import org.castruu.regions.repository.RegionRepository;

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

        servicesManager.register(RegionRepository.class, regionRepository, this, ServicePriority.High);

        Bukkit.getPluginManager().registerEvents(new JoinEventListener(), this);
    }

    @Override
    public void onDisable() {
        mongoDatabaseProvider.close();
    }

}
