package com.supaham.supaarrows;

import com.supaham.supaarrows.Listener.ArrowListener;
import com.supaham.supaarrows.Listener.PlayerListener;
import com.supaham.supaarrows.arrows.ArrowManager;
import com.supaham.supaarrows.commands.SupaCommand;
import com.supaham.supaarrows.config.ConfigHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class SupaArrows extends JavaPlugin implements Listener {

    private static SupaArrows plugin;
    private ConfigHandler configHandler;
    private ArrowManager arrowManager;

    @Override
    public void onEnable() {
        plugin = this;
        this.configHandler = new ConfigHandler(this);
        this.arrowManager = new ArrowManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new ArrowListener(this), this);
        getCommand("supaarrows").setExecutor(new SupaCommand(this));
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
     * @return instance of {@link com.supaham.supaarrows.config.ConfigHandler}
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
