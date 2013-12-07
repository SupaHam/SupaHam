package com.supaham.supaarrows.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * Represents a custom configurable yaml file.
 */
public class CustomYaml {

    private File file;
    private FileConfiguration config;

    private Plugin plugin;

    /**
     * Constructs a new CustomYaml object.
     *
     * @param instance instance of the TenJava plugin creating this file
     * @param fileName the file name
     */
    public CustomYaml(Plugin instance, String fileName) {
        this.plugin = instance;
        this.file = new File(plugin.getDataFolder(), fileName);
    }

    /**
     * Reloads the FileConfiguration.
     */
    public void reloadConfig() {

        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Saves the FileConfiguration.
     */
    public void saveConfig() {
        if (config == null) {
            return;
        }
        try {
            config.save(file);
        } catch (Exception e) {
            plugin.getLogger().severe("Error occurred while saving file: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Gets the FileConfiguration. If it is null, it calls {@link #reloadConfig()}.
     *
     * @return the FileConfiguration object
     */
    public FileConfiguration getConfig() {
        return getConfig(false);
    }

    /**
     * Gets the FileConfiguration. If it is null, it calls {@link #reloadConfig()}.
     *
     * @param reload true if the configuration should reload, otherwise false
     * @return the FileConfiguration object
     */
    public FileConfiguration getConfig(boolean reload) {
        if (config == null || reload) {
            reloadConfig();
        }
        return config;
    }

    public boolean exists() {
        return file != null && file.exists();
    }

    /**
     * Saves the default configuration file
     */
    public void saveDefaultConfig() {
        if (exists()) {
            return;
        }
        plugin.saveResource(file.getName(), false);
    }
}
