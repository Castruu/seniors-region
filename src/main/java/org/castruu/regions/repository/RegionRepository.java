package org.castruu.regions.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.lang.NonNull;
import org.castruu.regions.database.MongoDatabaseProvider;
import org.castruu.regions.entities.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

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
        FindIterable<Region> findRegion = regionCollection.find(eq("_id", id.toString()));
        return findRegion.first();
    }

    @Override
    public Region create(@NonNull Region entity) {
        regionCollection.insertOne(entity);
        return entity;
    }

    @Override
    public Region update(@NonNull Region entity) {
        return regionCollection.findOneAndReplace(
                eq("_id", entity.getUuid().toString()),
                entity
        );
    }

    @Override
    public void delete(@NonNull Region entity) {
        regionCollection.deleteOne(eq("_id", entity.getUuid().toString()));
    }
}
