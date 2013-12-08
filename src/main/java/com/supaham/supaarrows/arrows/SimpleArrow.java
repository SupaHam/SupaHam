package com.supaham.supaarrows.arrows;

import com.supaham.supaarrows.Listener.ArrowListener;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

/**
 * Represents an Arrow with some extra useful methods.
 */
public abstract class SimpleArrow implements Arrow {

    protected ArrowManager arrowManager;
    private String pathPrefix;

    public SimpleArrow(ArrowManager instance) {
        this.arrowManager = instance;
        this.pathPrefix = this.arrowManager.PARENT_NODE + "." + this.getName();
        this.arrowManager.getPlugin().getServer().addRecipe(getRecipe());
    }

    @Override
    public String getDisplayName() {
        if (!this.contains("display-name")) {
            this.set("display-name", getName());
        }
        return ChatColor.translateAlternateColorCodes('&', getString("display-name", getName()));
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(getDisplayName());
        meta.setLore(Arrays.asList(getSummary()));
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Checks if the {@code entity} is an arrow and it's shooter is a player and the arrow has the appropariate metadata.
     *
     * @param entity entity to check
     * @return true if the {@code entity} is an Arrow, it's shooter is a Player and has the appropriate metadata, otherwise false.
     */
    public boolean isArrow(Entity entity) {
        // We only care for Arrow entities that damage.
        if (!(entity instanceof org.bukkit.entity.Arrow)) {
            return false;
        }
        org.bukkit.entity.Arrow arrow = (org.bukkit.entity.Arrow) entity;
        // Make sure the shooter is a player.
        if (!(arrow.getShooter() instanceof Player)) {
            return false;
        }
        String metadata = arrow.getMetadata(ArrowListener.ARROW_METADATA).get(0).asString();
        if (!metadata.equals(getName())) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the {@code item} belongs to this Arrow object.
     */
    public boolean isSimilar(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }

        if (meta.getDisplayName().equals(getDisplayName()) && meta.getLore().get(0).equals(getSummary())) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the {@code arrow} is enabled.
     *
     * @return true if this arrow is enabled, otherwise false
     */
    public boolean isEnabled() {
        if (!this.contains("enabled")) {
            this.set("enabled", true);
        }
        return getBoolean("enabled", true);
    }

    /**
     * Checks if the arrows configuration contains the {@code path}.
     *
     * @param path path to check
     * @return true if the config contains the {@code path}
     */
    public boolean contains(String path) {
        return this.arrowManager.getCustomYaml().getConfig().contains(pathPrefix + "." + path);
    }

    /**
     * Gets a String value from the {@code property}. <p />
     * Parent prefix is not needed in this method, ex: to get 'arrows.{arrowName}.enabled', set path to enabled.
     *
     * @param path path to get
     * @return the value
     */
    public String getString(String path) {
        return getString(path, null);
    }

    /**
     * Gets a String value from the {@code property}. <p />
     * Parent prefix is not needed in this method, ex: to get 'arrows.{arrowName}.enabled', set path to enabled.
     *
     * @param path path to get
     * @param def  default value to return
     * @return the value
     */
    public String getString(String path, String def) {
        return this.arrowManager.getCustomYaml().getConfig().getString(pathPrefix + "." + path, def);
    }

    /**
     * Gets a Boolean value from the {@code property}. <p />
     * Parent prefix is not needed in this method, ex: to get 'arrows.{arrowName}.enabled', set path to enabled.
     *
     * @param path path to get
     * @return the value
     */
    public Boolean getBoolean(String path) {
        return getBoolean(path, false);
    }

    /**
     * Gets a Boolean value from the {@code property}. <p />
     * Parent prefix is not needed in this method, ex: to get 'arrows.{arrowName}.enabled', set path to enabled.
     *
     * @param path path to get
     * @param def  default value to return
     * @return the value
     */
    public Boolean getBoolean(String path, boolean def) {
        return this.arrowManager.getCustomYaml().getConfig().getBoolean(pathPrefix + "." + path, def);
    }

    /**
     * Gets an Integer value from the {@code property}. <p />
     * Parent prefix is not needed in this method, ex: to get 'arrows.{arrowName}.enabled', set path to enabled.
     *
     * @param path path to get
     * @return the value
     */
    public Integer getInt(String path) {
        return getInt(path, 0);
    }

    /**
     * Gets an Integer value from the {@code property}. <p />
     * Parent prefix is not needed in this method, ex: to get 'arrows.{arrowName}.enabled', set path to enabled.
     *
     * @param path path to get
     * @param def  default value to return
     * @return the value
     */
    public Integer getInt(String path, Integer def) {
        return this.arrowManager.getCustomYaml().getConfig().getInt(pathPrefix + "." + path, def);
    }

    /**
     * Gets a Double value from the {@code property}. <p />
     * Parent prefix is not needed in this method, ex: to get 'arrows.{arrowName}.enabled', set path to enabled.
     *
     * @param path path to get
     * @return the value
     */
    public Double getDouble(String path) {
        return getDouble(path, 0D);
    }

    /**
     * Gets a Double value from the {@code property}. <p />
     * Parent prefix is not needed in this method, ex: to get 'arrows.{arrowName}.enabled', set path to enabled.
     *
     * @param path path to get
     * @param def  default value to return
     * @return the value
     */
    public Double getDouble(String path, Double def) {
        return this.arrowManager.getCustomYaml().getConfig().getDouble(pathPrefix + "." + path, def);
    }

    /**
     * Gets a List value from the {@code property}. <p />
     * Parent prefix is not needed in this method, ex: to get 'arrows.{arrowName}.enabled', set path to enabled.
     *
     * @param path path to get
     * @return the value
     */
    public List<String> getList(String path) {
        return this.arrowManager.getCustomYaml().getConfig().getStringList(pathPrefix + "." + path);
    }

    /**
     * Sets the {@code value} at the {@code path} in the arrows yaml configuration. <p />
     * Parent prefix is not needed in this method, ex: to set 'arrows.{arrowName}.enabled', set path to enabled.
     *
     * @param path  path to set
     * @param value value to set
     */
    public void set(String path, Object value) {
        this.arrowManager.getCustomYaml().getConfig().set(pathPrefix + "." + path, value);
    }


}
