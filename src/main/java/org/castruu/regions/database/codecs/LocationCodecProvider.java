package org.castruu.regions.database.codecs;

import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistry;
import org.bukkit.Location;

public class LocationCodecProvider implements org.bson.codecs.configuration.CodecProvider {
    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry codecRegistry) {
        if(clazz == Location.class) {
            return (Codec<T>) new LocationCodec();
        }

        return null;
    }
}
