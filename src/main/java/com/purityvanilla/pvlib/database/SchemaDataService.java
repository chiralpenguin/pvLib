package com.purityvanilla.pvlib.database;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.plugin.java.JavaPlugin;

public class SchemaDataService extends DataService {
    private final SchemaOperator operator;
    private final int currentVersion = 3;

    public SchemaDataService(JavaPlugin plugin, DatabaseConnector database) {
        super(plugin);
        operator = new SchemaOperator(database);
        int dbVersion = operator.getDBVersion();

        // Set stored schema version to current version if no results (assumes entire database is fresh)
        if (dbVersion == 0) {
            Component message = Component.text("<aqua>No existing database schema version found, creating tables for current version.");
            plugin.getLogger().info(PlainTextComponentSerializer.plainText().serialize(message));
            updateSchemaVersion();
        }
    }

    @Override
    public void saveAll() {
        operator.updateSchemaVersion(currentVersion);
    }

    public int getCurrentVersion() {
        return currentVersion;
    }

    public int getDBVersion() {
        return operator.getDBVersion();
    }

    public void updateSchemaVersion() {
        operator.updateSchemaVersion(currentVersion);
    }
}
