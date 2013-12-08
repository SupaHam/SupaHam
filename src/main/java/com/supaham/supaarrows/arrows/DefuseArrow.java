package com.supaham.supaarrows.arrows;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents an Arrow that defuses explosive entities
 */
public class DefuseArrow extends SimpleArrow {

    private Set<Entity> entities;

    public DefuseArrow(ArrowManager instance) {
        super(instance);
        this.entities = new HashSet<>();
    }

    @Override
    public String getName() {
        return "defusearrow";
    }

    @Override
    public String getSummary() {
        return "Defuses explosive entities.";
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
        recipe.setIngredient('y', Material.ICE);
        return recipe;
    }

    /**
     * Handles creepers.
     *
     * @param event
     */
    @EventHandler
    public void onEntityDamage(final EntityDamageByEntityEvent event) {
        if (!isArrow(event.getDamager())) {
            return;
        }
        if (!(event.getEntity() instanceof Creeper)) {
            return;
        }
        Creeper creeper = (Creeper) event.getEntity();
        // Make sure the shooter is a player and not a skeleton.

        Player shooter = (Player) ((Projectile) event.getDamager()).getShooter();
        int duration = getCreeperDefuseSeconds();
        // Remove the creeper if it is already in there.
        if (containsEntity(creeper)) {
            removeCreeper(creeper);
        }
        addCreeper(event.getEntity());

        // Schedule task to remove creeper
        new BukkitRunnable() {
            @Override
            public void run() {
                removeCreeper(event.getEntity());
            }
        }.runTaskLater(this.arrowManager.getPlugin(), duration * 20);

        shooter.sendMessage(String.format("%sYou defused a Creeper for %s%d%s seconds!", ChatColor.YELLOW, ChatColor.GREEN,
                duration, ChatColor.YELLOW));
        event.setDamage(0);
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        if (containsEntity(event.getEntity())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!isArrow(event.getEntity())) {
            return;
        }
        for (Entity nearby : event.getEntity().getNearbyEntities(1, 1, 1)) {
            if (nearby instanceof TNTPrimed) {
                nearby.remove();
            }
        }
    }

    // Targetting doesn't work very well with Bukkit API.
    //@EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent event) {
        resetTarget(event.getEntity());
    }


    /**
     * Resets the {@code entity}'s target.
     *
     * @param entity entity to reset
     * @deprecated this method has no use
     */
    @Deprecated
    public void resetTarget(Entity entity) {
        if (entity instanceof Creeper && containsEntity(entity)) {
            // TODO notify the player
            Location location = entity.getLocation();
            entity.remove();
            Creeper creeper = (Creeper) location.getWorld().spawnEntity(location, EntityType.CREEPER);
            creeper.setTarget(creeper);
        }
    }

    /**
     * Gets the duration (in seconds) a creeper should stay defused.
     *
     * @return the duration
     */
    public int getCreeperDefuseSeconds() {
        if (!this.contains("creeper-defuse")) {
            this.set("creeper-defuse", 15);
        }
        return this.getInt("creeper-defuse", 15);
    }

    /**
     * Gets the duration (in seconds) a tnt should stay defused.
     *
     * @return the duration
     * @deprecated this method has no use
     */
    @Deprecated
    public int getTntDefuseSeconds() {
        if (!this.contains("tnt-defuse")) {
            this.set("tnt-defuse", 10);
        }
        return this.getInt("tnt-defuse", 10);
    }


    /**
     * Checks if the {@code entity} is in the Collection of entities that can not explode.
     *
     * @param entity entity to check
     * @return true if the entity is in the Collection, otherwise false
     */
    public boolean containsEntity(Entity entity) {
        return entities.contains(entity);
    }

    /**
     * Adds the {@code entity} to the Collection of entities.
     *
     * @param entity entity to add
     * @return true if the entity was added, otherwise false
     */
    public boolean addCreeper(Entity entity) {
        boolean add = entities.add(entity);
        return add;
    }

    /**
     * Removes the {@code entity} from the Collection of entites.
     *
     * @param entity entity to remove
     * @return true if the entity was removed, otherwise false
     */
    public boolean removeCreeper(Entity entity) {
        return entities.remove(entity);
    }
}
