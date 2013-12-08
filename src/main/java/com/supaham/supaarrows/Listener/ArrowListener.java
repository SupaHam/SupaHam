package com.supaham.supaarrows.Listener;

import com.supaham.supaarrows.SupaArrows;
import com.supaham.supaarrows.arrows.SimpleArrow;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.metadata.FixedMetadataValue;

public class ArrowListener implements Listener {

    private SupaArrows plugin;

    public static final String ARROW_METADATA = "supaarrows";

    public ArrowListener(SupaArrows instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Arrow)) {
            return;
        }
        Arrow projectile = (Arrow) event.getEntity();
        if (!(projectile.getShooter() instanceof Player)) {
            return;
        }
        Player player = (Player) projectile.getShooter();
        PlayerInventory inventory = player.getInventory();
        int slot = inventory.getHeldItemSlot() + 1;
        ItemStack arrowItem = inventory.getItem(slot);
        if (arrowItem == null || !arrowItem.getType().equals(Material.ARROW)) {
            return;
        }

        // TODO figure out a better way to check if arrow is a custom arrow
        for (com.supaham.supaarrows.arrows.Arrow arrow : plugin.getArrowManager().getArrows().values()) {
            if (((SimpleArrow) arrow).isSimilar(arrowItem)) {
                projectile.setMetadata(ARROW_METADATA, new FixedMetadataValue(plugin, arrow.getName()));
            }
        }
    }
}
