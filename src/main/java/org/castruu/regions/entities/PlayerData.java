package org.castruu.regions.entities;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

public class PlayerData {

    @BsonProperty("name")
    private String name;

    @BsonProperty("uuid")
    private UUID uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
