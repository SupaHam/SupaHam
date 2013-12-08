package com.supaham.supaarrows.arrows;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

/**
 * Represents an Arrow. <br /> Use {@link com.supaham.supaarrows.arrows.SimpleArrow} for implementation.
 */
public interface Arrow extends Listener {

    /**
     * Gets the name of this arrow.
     *
     * @return the name
     */
    public String getName();

    /**
     * Gets the display name of this arrow.
     *
     * @return the display name
     */
    public String getDisplayName();

    /**
     * Gets the summary (short description).
     *
     * @return the summary
     */
    public String getSummary();

    /**
     * Gets the description (long description).
     *
     * @return the description
     */
    public String getDescription();

    /**
     * Gets the ItemStack that is returned for using this arrow.
     *
     * @return the {@link ItemStack} can not be null.
     */
    public ItemStack getItemStack();

    /**
     * Recipe to create this type of arrow.
     *
     * @return the {@link Recipe} object
     */
    public Recipe getRecipe();
}
