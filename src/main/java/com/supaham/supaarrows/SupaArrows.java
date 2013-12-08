package com.supaham.supaarrows;

import com.supaham.supaarrows.Listener.ArrowListener;
import com.supaham.supaarrows.arrows.ArrowManager;
import com.supaham.supaarrows.config.ConfigHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SupaArrows extends JavaPlugin implements Listener {

    private static SupaArrows plugin;
    private ConfigHandler configHandler;
    private ArrowManager arrowManager;

    @Override
    public void onEnable() {
        plugin = this;
        this.configHandler = new ConfigHandler(this);
        getServer().getPluginManager().registerEvents(this, this);
        this.arrowManager = new ArrowManager(this);
        getServer().getPluginManager().registerEvents(new ArrowListener(this), this);
    }

    @Override
    public void onDisable() {
        this.arrowManager.getCustomYaml().saveConfig();
    }

    /**
     * Gets the instance of this plugin.
     *
     * @return instance of this class
     */
    public static SupaArrows getInstance() {
        return plugin;
    }

    /**
     * Gets the instance of {@link ConfigHandler} belonging to this instance of the plugin.
     *
     * @return instance of
     */
    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    /**
     * Gets the instance of {@link ArrowManager} belonging to this instance of the plugin.
     *
     * @return instance of {@link com.supaham.supaarrows.arrows.ArrowManager}
     */
    public ArrowManager getArrowManager() {
        return arrowManager;
    }
}
