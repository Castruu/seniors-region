package org.castruu.regions.database.codecs;

import org.bson.BsonBinary;
import org.bson.BsonBinarySubType;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.UuidRepresentation;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.internal.UuidHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class LocationCodec implements Codec<Location> {
    @Override
    public Location decode(BsonReader bsonReader, DecoderContext decoderContext) {
        bsonReader.readStartDocument();
        double x = bsonReader.readDouble("x");
        double y = bsonReader.readDouble("y");
        double z = bsonReader.readDouble("z");
        UUID worldUUID = bsonReader.readBinaryData("world").asUuid();
        bsonReader.readEndDocument();
        return new Location(Bukkit.getWorld(worldUUID), x, y, z);
    }

    @Override
    public void encode(BsonWriter bsonWriter, Location location, EncoderContext encoderContext) {
        bsonWriter.writeStartDocument();
        bsonWriter.writeName("x");
        bsonWriter.writeDouble(location.getX());
        bsonWriter.writeName("y");
        bsonWriter.writeDouble(location.getY());
        bsonWriter.writeName("z");
        bsonWriter.writeDouble(location.getZ());
        bsonWriter.writeName("world");
        byte[] binaryData = UuidHelper.encodeUuidToBinary(location.getWorld().getUID(), UuidRepresentation.STANDARD);
        bsonWriter.writeBinaryData(new BsonBinary(BsonBinarySubType.UUID_STANDARD, binaryData));
        bsonWriter.writeEndDocument();
    }

    @Override
    public Class<Location> getEncoderClass() {
        return Location.class;
    }
}
