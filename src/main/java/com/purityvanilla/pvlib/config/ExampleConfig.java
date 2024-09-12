package com.purityvanilla.pvlib.config;

import org.spongepowered.configurate.CommentedConfigurationNode;

import java.util.HashMap;
import java.util.Map;

public class ExampleConfig extends ConfigFile {
    private final Boolean exampleOption;
    private final HashMap<String, String> exampleMap;

    public ExampleConfig(String filepath) {
        // Create config file and save default config (located at resources/config.yml)
        super(filepath);

        // Example boolean and Map<String, String> config values
        exampleOption = configRoot.node("example").getBoolean();
        exampleMap = new HashMap<>();
        Map<Object, CommentedConfigurationNode> entries = configRoot.node("exampleMap").childrenMap();
        for (Map.Entry<Object, CommentedConfigurationNode> entry : entries.entrySet()) {
            exampleMap.put(entry.getKey().toString(), entry.getValue().getString());
        }
    }

    // Example getter methods for config values
    public Boolean exampleOption() {
        return exampleOption;
    }

    public String exampleValue() {
        return exampleMap.get("example");
    }
}
