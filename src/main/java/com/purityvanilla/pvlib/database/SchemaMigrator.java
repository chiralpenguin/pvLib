package com.purityvanilla.pvlib.database;

import com.purityvanilla.pvlib.database.migration.Migration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SchemaMigrator {
    private final SchemaDataService schemaData;
    private final Logger logger;
    private final List<Migration> migrations;

    public SchemaMigrator(SchemaDataService schemaData, Logger logger) {
        this.schemaData = schemaData;
        this.logger = logger;

        migrations = new ArrayList<>();
    }

    private void registerMigration(Migration migration) {
        migrations.add(migration);
    }

    public void handleMigrations() {
        int currentVersion = schemaData.getCurrentVersion();
        int dbVersion = schemaData.getDBVersion();

        if (dbVersion >= currentVersion) {
            return;
        }

        TagResolver resolver = TagResolver.resolver(
                Placeholder.component("currentversion", Component.text(currentVersion)),
                Placeholder.component("dbversion", Component.text(dbVersion))
        );
        Component message = Component.text("<aqua>The pvCore database is being updated to version <currentversion> (currently <dbversion>)");
        logger.info(PlainTextComponentSerializer.plainText().serialize(message));

        for (Migration migration : migrations) {
            if (dbVersion < migration.getVersion()) {
                migration.migrate();
            }
        }

        schemaData.updateSchemaVersion();
    }
}