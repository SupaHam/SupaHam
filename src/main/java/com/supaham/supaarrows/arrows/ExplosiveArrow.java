package com.supaham.supaarrows.arrows;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

/**
 * Represents an Arrow that creates an explosion where ever it lands.
 */
public class ExplosiveArrow extends SimpleArrow {

    public ExplosiveArrow(ArrowManager instance) {
        super(instance);
    }

    @Override
    public String getName() {
        return "explosivearrow";
    }

    @Override
    public String getSummary() {
        return "Creates an explosion where ever the arrow lands.";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(getItemStack());
        recipe.shape(" y "," y "," x ");
        recipe.setIngredient('x', Material.ARROW);
        recipe.setIngredient('y', Material.TNT);
        return recipe;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {

        if (!isArrow(event.getEntity())) {
            return;
        }

        Player player = (Player) event.getEntity().getShooter();
        TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(event.getEntity().getLocation(), EntityType.PRIMED_TNT);
        tnt.setFuseTicks(0);
        event.getEntity().remove();
    }
}
