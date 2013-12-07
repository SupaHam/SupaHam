package com.supaham.supaarrows.config;

import com.supaham.supaarrows.SupaArrows;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

/**
 * Represents a Config file belonging to
 */
public class ConfigHandler {

    public static final Property<String> MESSAGE = new Property<>("message", "This is a default message!");

    private CustomYaml yaml;

    /**
     * Constructs a new ConfigHandler instance that handles the config.yml file in the {@code instance}'s data folder.
     *
     * @param instance instance of the plugin creating this
     */
    public ConfigHandler(SupaArrows instance) {
        yaml = new CustomYaml(instance, "config.yml");
        reloadConfig();
    }

    /**
     * Gets a String value from the {@code property}.
     *
     * @param property property to get
     * @return the value
     */
    public String getString(Property<String> property) {
        return getConfig().getString(property.getPath(), property.getDefaultValue());
    }

    /**
     * Gets a Boolean value from the {@code property}.
     *
     * @param property property to get
     * @return the value
     */
    public Boolean getBoolean(Property<Boolean> property) {
        return getConfig().getBoolean(property.getPath(), property.getDefaultValue());
    }

    /**
     * Gets an Integer value from the {@code property}.
     *
     * @param property property to get
     * @return the value
     */
    public Integer getInt(Property<Integer> property) {
        return getConfig().getInt(property.getPath(), property.getDefaultValue());
    }

    /**
     * Gets a Double value from the {@code property}.
     *
     * @param property property to get
     * @return the value
     */
    public Double getDouble(Property<Double> property) {
        return getConfig().getDouble(property.getPath(), property.getDefaultValue());
    }

    /**
     * Gets a List value from the {@code property}.
     *
     * @param property property to get
     * @return the value
     */
    public List<String> getList(Property<List<String>> property) {
        return getConfig().getStringList(property.getPath());
    }

    public void reloadConfig() {
        getCustomYaml().saveDefaultConfig();
        getCustomYaml().reloadConfig();
    }

    public FileConfiguration getConfig() {
        return getCustomYaml().getConfig();
    }

    /**
     * Gets the ConfigObject.
     *
     * @return the CustomYaml object
     */
    private CustomYaml getCustomYaml() {
        return yaml;
    }
}
