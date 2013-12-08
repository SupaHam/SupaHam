package com.supaham.supaarrows.commands;

import com.supaham.supaarrows.SupaArrows;
import com.supaham.supaarrows.arrows.Arrow;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SupaCommand implements CommandExecutor {

    private SupaArrows plugin;

    public SupaCommand(SupaArrows instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You aren't a player!");
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Type " + ChatColor.ITALIC + "/" + label + " help" + ChatColor.RED + " for help!");
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(format(label + "help"));
            sender.sendMessage(ChatColor.YELLOW + "reload: " + ChatColor.AQUA + "Reloads the configuration files.");
            sender.sendMessage(ChatColor.YELLOW + "list: " + ChatColor.AQUA + "Displays all the available arrows");
            sender.sendMessage(ChatColor.YELLOW + "{arrow}: " + ChatColor.AQUA + "Gievs you a stack of the name of the arrow you type.");
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            plugin.getArrowManager().reloadConfig();
            plugin.getArrowManager().reloadArrows();
            player.sendMessage(ChatColor.YELLOW + "SupaArrows configuration files reloaded successfully!");
            return true;
        } else if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(format("Arrow list"));
            for (Arrow arrow : plugin.getArrowManager().getArrows().values()) {
                player.sendMessage(ChatColor.YELLOW + arrow.getName() + ": " + ChatColor.AQUA + arrow.getSummary());
            }
            return true;
        } else {

            Arrow arrow = plugin.getArrowManager().getArrow(args[0]);
            if (arrow == null) {
                sender.sendMessage(ChatColor.RED + "That is not a valid arrow!");
            }

            ItemStack item = arrow.getItemStack().clone();
            item.setAmount(64);
            player.getInventory().addItem(item);
            sender.sendMessage(ChatColor.YELLOW + "You received a stack of " + arrow.getDisplayName() + ChatColor.YELLOW + "!");
            return true;
        }
    }

    /**
     * Creates a pretty header.
     *
     * @param header header to set
     * @return formatted String
     */
    public String format(String header) {
        return ChatColor.BLUE.toString() + ChatColor.STRIKETHROUGH + "----" + ChatColor.RESET + " " + header + " " + ChatColor.BLUE + ChatColor
                .STRIKETHROUGH + "----";
    }
}
