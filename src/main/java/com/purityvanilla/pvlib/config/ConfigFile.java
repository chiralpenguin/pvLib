package com.purityvanilla.pvlib.config;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
    protected Messages messages;

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

    public String getRawMessage(String key) {
        return messages.getRawMessage(key);
    }

    public Component getMessage(String key) {
        if (messages == null) {
            return Component.text("Messages have not been initialised in the config!").color(NamedTextColor.RED);
        }

        return messages.getMessage(key);
    }
}
