package com.supaham.supaarrows.arrows;

import com.supaham.supaarrows.SupaArrows;
import com.supaham.supaarrows.config.CustomYaml;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents an ArrowManager that handles loading and unloading arrows.
 */
public class ArrowManager {

    private SupaArrows plugin;
    private CustomYaml yaml;
    private final Map<String, Arrow> arrows;

    /**
     * Parent node used in configuration methods
     */
    public final String PARENT_NODE = "arrows";

    public ArrowManager(SupaArrows instance) {
        this.plugin = instance;
        reloadConfig();

        this.arrows = new HashMap<>();
        loadArrows();
    }

    /**
     * Reloads the ArrowManager
     */
    public void reloadConfig() {
        // The yaml should only be once null when the ArrowManager has been constructed.
        if (this.yaml == null) {
            this.yaml = new CustomYaml(plugin, "arrows.yml");
        }

        this.yaml.saveDefaultConfig();
        this.yaml.reloadConfig();
    }

    /**
     * Checks if arrows can be loaded and loads them. Configuration must be loaded before executig this.
     */
    public void loadArrows() {

        if (this.yaml == null) {
            plugin.getLogger().severe("Tried to load arrows before configuration was loaded! Please report this.");
            return;
        }

        addArrow(new DefuseArrow(this));
        addArrow(new TeleportArrow(this));
        addArrow(new ExplosiveArrow(this));
        addArrow(new SquidArrow(this));
    }

    /**
     * Clears and executes {@link #loadArrows()}.
     */
    public void reloadArrows() {
        for (Iterator<Arrow> it = arrows.values().iterator(); it.hasNext(); ) {
            Arrow arrow = it.next();
            removeArrow(arrow);
        }
        this.loadArrows();
    }

    /**
     * Gets the Map of loaded {@link com.supaham.supaarrows.arrows.Arrow}s.
     *
     * @return
     */
    public Map<String, Arrow> getArrows() {
        return arrows;
    }

    /**
     * Gets an arrow by name
     *
     * @param name name of the arrow to get
     * @return an {@link Arrow} if it exists, otherwise null
     */
    public Arrow getArrow(String name) {
        return this.arrows.get(name.toLowerCase());
    }


    /**
     * Adds an Arrow object to the Map of arrows.
     *
     * @param arrow arrow to add
     */
    public void addArrow(Arrow arrow) {
        if (!((SimpleArrow) arrow).isEnabled()) {
            return;
        }
        this.arrows.put(arrow.getName().toLowerCase(), arrow);
        this.plugin.getServer().getPluginManager().registerEvents(arrow, plugin);
    }

    /**
     * Removes the {@code arrow} object.
     *
     * @param arrow arrow to remove
     * @return the removed Arrow, can be nulls
     */
    public Arrow removeArrow(Arrow arrow) {
        return removeArrow(arrow.getName());
    }

    /**
     * Removes an {@link Arrow} object by {@code name}.
     *
     * @param name name of the arrow to remove
     * @return the removed Arrow, can be null
     */
    public Arrow removeArrow(String name) {
        Arrow arrow = this.arrows.remove(name.toLowerCase());
        Iterator<Recipe> recipes = plugin.getServer().recipeIterator();
        while (recipes.hasNext()) {
            Recipe recipe = recipes.next();
            if (recipe.getResult().isSimilar(arrow.getItemStack())) {
                recipes.remove();
            }
        }
        HandlerList.unregisterAll(arrow);
        return arrow;
    }

    /**
     * Gets the SupaArrows instance that created this ArrowManager instance.
     *
     * @return {@link com.supaham.supaarrows.SupaArrows} instance
     */
    public SupaArrows getPlugin() {
        return plugin;
    }

    /**
     * Gets the {@link CustomYaml} object that handles Arrow configuration.
     *
     * @return CustomYaml object
     */
    public CustomYaml getCustomYaml() {
        return yaml;
    }

}
