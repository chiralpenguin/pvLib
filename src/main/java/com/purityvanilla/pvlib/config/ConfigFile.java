package com.purityvanilla.pvlib.config;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigFile {
    private final String filepath;
    protected CommentedConfigurationNode configRoot;

    public ConfigFile(String filepath) {
        this.filepath = filepath;
        File file = new File(filepath);
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            try {
                InputStream configStream = getClass().getResourceAsStream("/config.yml");
                Files.copy(configStream, Paths.get(file.toURI()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        YamlConfigurationLoader configLoader = YamlConfigurationLoader.builder().path(Path.of(filepath)).build();
        try {
            configRoot = configLoader.load();
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }
}
