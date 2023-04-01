package org.castruu.regions.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.castruu.regions.database.codecs.MinecraftCodecProvider;
import org.castruu.regions.entities.PlayerData;
import org.castruu.regions.entities.Region;

public class MongoDatabaseProvider {

    private final JavaPlugin plugin;
    private MongoClient mongoClient;
    private MongoDatabase database;


    private final String mongoDbConnectionString;
    private final String databaseName;

    public MongoDatabaseProvider(JavaPlugin plugin) {
        this.plugin = plugin;
        mongoDbConnectionString = plugin.getConfig().getString("mongodb.connection-string");
        databaseName = plugin.getConfig().getString("mongodb.database");
    }


    public void connect() {
        ConnectionString connectionString = new ConnectionString(mongoDbConnectionString);
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(
                PojoCodecProvider.builder().register(Region.class).register(PlayerData.class).build(),
                new MinecraftCodecProvider()
        );
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                pojoCodecRegistry);

        MongoClientSettings settings = MongoClientSettings.builder()
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

       mongoClient = MongoClients.create(settings);
       database = mongoClient.getDatabase(databaseName);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void close() {
        mongoClient.close();
    }


}
