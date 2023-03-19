package org.castruu.regions.entities;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bukkit.Location;

import java.util.UUID;

public class Region {
    @BsonId
    private UUID uuid;

    @BsonProperty("location")
    private Location location;

    public Region() {}

    public Region(UUID uuid, Location location) {
        this.uuid = uuid;
        this.location = location;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
