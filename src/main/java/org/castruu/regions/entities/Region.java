package org.castruu.regions.entities;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Region {

    @BsonId
    private ObjectId id;

    @BsonProperty("name")
    private String name;

    @BsonProperty("start")
    private Location start;

    @BsonProperty("end")
    private Location end;

    @BsonProperty("whitelist")
    private Set<PlayerData> whitelist = new HashSet<>(0);

    @BsonProperty("owner")
    private PlayerData owner;

    public Region() {}

    public Region(ObjectId id, Location start, Location end, Set<PlayerData> whitelist, PlayerData owner) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.whitelist = whitelist;
        this.owner = owner;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
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

    public Set<PlayerData> getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(Set<PlayerData> whitelist) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Region region = (Region) o;

        return Objects.equals(id, region.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
