package com.supaham.supaarrows.arrows;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Represents an Arrow that teleports its shooter to the location it landed at.
 */
public class TeleportArrow extends SimpleArrow {

    public TeleportArrow(ArrowManager instance) {
        super(instance);
    }

    @Override
    public String getName() {
        return "teleportarrow";
    }

    @Override
    public String getSummary() {
        return "Teleports the shooter wherever it lands!";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getItemStack());
        recipe.shape(" y ", " y ", " x ");
        recipe.setIngredient('x', Material.ARROW);
        recipe.setIngredient('y', Material.ENDER_PEARL);
        return recipe;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!isArrow(event.getEntity())) {
            return;
        }

        Player player = (Player) event.getEntity().getShooter();
        Location location = event.getEntity().getLocation();
        if (!isSafe(location.clone())) {
            player.sendMessage(ChatColor.RED + "That location is not safe!");
            return;
        }
        event.getEntity().remove();
        location.setPitch(player.getLocation().getPitch());
        location.setYaw(player.getLocation().getYaw());
        player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    /**
     * Checks to see if the {@code location} is player-safe.
     *
     * @param location location to check
     * @return true if the location is safe.
     */
    public boolean isSafe(Location location) {
        if (location != null) {
            if (!location.getBlock().getType().equals(Material.AIR)) {
                return false;
            }
            location.setY(location.getY() + 1);
            return location.getBlock().getType().equals(Material.AIR);
        }
        return false;
    }
}
