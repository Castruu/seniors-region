package org.castruu.regions.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.lang.NonNull;
import org.castruu.regions.database.MongoDatabaseProvider;
import org.castruu.regions.entities.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegionRepository implements Repository<Region, UUID> {


    private final MongoCollection<Region> regionCollection;

    public RegionRepository(MongoDatabaseProvider databaseProvider) {
        this.regionCollection = databaseProvider.getDatabase().getCollection("regions", Region.class);
    }


    @Override
    public List<Region> findAll() {
        List<Region> regions = new ArrayList<>();
        FindIterable<Region> regionsIterable = regionCollection.find();
        regionsIterable.forEach(regions::add);
        return regions;
    }

    @Override
    public Region find(@NonNull UUID id) {
        return null;
    }

    @Override
    public Region create(@NonNull Region entity) {
        InsertOneResult insertOneResult = regionCollection.insertOne(entity);
        return entity;
    }

    @Override
    public Region update(@NonNull Region entity) {
        return null;
    }

    @Override
    public void delete(@NonNull Region entity) {

    }
}
