package org.castruu.regions.entities;

import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class Region {
    @BsonId
    private UUID uuid;

    @BsonProperty("name")
    private String name;

    @BsonProperty("start")
    private Location start;

    @BsonProperty("end")
    private Location end;

    @BsonProperty("whitelist")
    private List<PlayerData> whitelist;

    @BsonProperty("owner")
    private PlayerData owner;

    public Region() {}

    public Region(UUID uuid, Location start, Location end, List<PlayerData> whitelist, PlayerData owner) {
        this.uuid = uuid;
        this.start = start;
        this.end = end;
        this.whitelist = whitelist;
        this.owner = owner;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Location getStart() {
        return start;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public List<PlayerData> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(List<PlayerData> whitelist) {
        this.whitelist = whitelist;
    }

    public PlayerData getOwner() {
        return owner;
    }

    public void setOwner(PlayerData owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
