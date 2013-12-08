package com.supaham.supaarrows.arrows;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Squid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

/**
 * Represents an Arrow that spawns a Squid.
 */
public class SquidArrow extends SimpleArrow {

    public SquidArrow(ArrowManager instance) {
        super(instance);
    }

    @Override
    public String getName() {
        return "squidarrow";
    }

    @Override
    public String getSummary() {
        return "a Squid spawns where ever the arrow lands";
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
        recipe.setIngredient('y', new MaterialData(Material.INK_SACK, (byte) 0));
        return recipe;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!isArrow(event.getEntity())) {
            return;
        }
        Player player = (Player) event.getEntity().getShooter();
        Projectile projectile = event.getEntity();
        Squid squid = (Squid) projectile.getWorld().spawnEntity(projectile.getLocation(), EntityType.SQUID);
        squid.setCustomName(ChatColor.BLUE + "Keith");
        squid.setCustomNameVisible(true);
        projectile.remove();
    }
}
