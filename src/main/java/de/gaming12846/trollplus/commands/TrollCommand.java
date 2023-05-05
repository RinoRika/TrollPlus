/*
 *
 *  * This file is part of TrollPlus.
 *  * Copyright (C) 2023 Gaming12846
 *
 */

package de.gaming12846.trollplus.commands;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.Constants;
import de.gaming12846.trollplus.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class TrollCommand implements CommandExecutor {
    private final TrollPlus plugin;

    public TrollCommand(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Constants.PLUGIN_PREFIX + label + Constants.PLUGIN_NO_CONSOLE);
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission(Constants.PERMISSION_ALL) || !player.hasPermission(Constants.PERMISSION_TROLL)) {
            player.sendMessage(Constants.PLUGIN_NO_PERMISSION);
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(Constants.PLUGIN_INVALID_SYNTAX + label + " <player>");
            return true;
        }

        FileConfiguration langConfig = plugin.getLanguageConfig().getConfig();

        Constants.TARGET = Bukkit.getPlayer(args[0]);
        if (Constants.TARGET == null) {
            player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll-player") + " " + ChatColor.BOLD + args[0] + ChatColor.RESET + " " + langConfig.getString("troll-not-online"));
            return true;
        }

        if (plugin.getBlocklistConfig().getConfig().contains(Constants.TARGET.getUniqueId().toString()) && !player.hasPermission(Constants.PERMISSION_IGNORE_IMMUNE)) {
            player.sendMessage(Constants.PLUGIN_PREFIX + langConfig.getString("troll-player") + " " + ChatColor.BOLD + Constants.TARGET.getName() + ChatColor.RESET + " " + langConfig.getString("troll-is-immune"));
            return true;
        }

        // Create troll menu
        Constants.TROLL_GUI = Bukkit.createInventory(null, 54, "Troll " + ChatColor.GOLD + ChatColor.BOLD + Constants.TARGET.getName());

        // Add features
        Constants.TROLL_GUI.setItem(4, ItemBuilder.createSkull(ChatColor.GOLD + Constants.TARGET.getName(), Constants.TARGET.getPlayer()));
        Constants.TROLL_GUI.setItem(47, ItemBuilder.createItemWithLore(Material.POTION, ChatColor.WHITE + langConfig.getString("troll-vanish") + " " + Constants.getVanishStatus(Constants.STATUS_VANISH), langConfig.getString("troll-vanish-description")));
        Constants.TROLL_GUI.setItem(48, ItemBuilder.createItemWithLore(Material.ENDER_PEARL, ChatColor.WHITE + langConfig.getString("troll-teleport"), langConfig.getString("troll-teleport-description")));
        Constants.TROLL_GUI.setItem(49, ItemBuilder.createItemWithLore(Material.WITHER_SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-kill"), langConfig.getString("troll-kill-description")));
        Constants.TROLL_GUI.setItem(50, ItemBuilder.createItemWithLore(Material.CHEST, ChatColor.WHITE + langConfig.getString("troll-invsee"), langConfig.getString("troll-invsee-description")));
        Constants.TROLL_GUI.setItem(51, ItemBuilder.createItemWithLore(Material.ENDER_CHEST, ChatColor.WHITE + langConfig.getString("troll-invsee-ender-chest"), langConfig.getString("troll-invsee-ender-chest-description")));
        Constants.TROLL_GUI.setItem(53, ItemBuilder.createItemWithLore(Material.BARRIER, ChatColor.RED + langConfig.getString("troll-close"), langConfig.getString("troll-close-description")));

        Constants.TROLL_GUI.setItem(10, ItemBuilder.createItemWithLore(Material.ICE, ChatColor.WHITE + langConfig.getString("troll-freeze") + " " + Constants.getFeatureStatus(Constants.STATUS_FREEZE), langConfig.getString("troll-freeze-description")));
        Constants.TROLL_GUI.setItem(12, ItemBuilder.createItemWithLore(Material.SHEARS, ChatColor.WHITE + langConfig.getString("troll-hand-item-drop") + " " + Constants.getFeatureStatus(Constants.STATUS_HAND_ITEM_DROP), langConfig.getString("troll-hand-item-drop-description")));
        Constants.TROLL_GUI.setItem(14, ItemBuilder.createItemWithLore(Material.LEAD, ChatColor.WHITE + langConfig.getString("troll-control") + " " + Constants.getFeatureStatus(Constants.STATUS_CONTROL), langConfig.getString("troll-control-description")));
        Constants.TROLL_GUI.setItem(16, ItemBuilder.createItemWithLore(Material.COMPASS, ChatColor.WHITE + langConfig.getString("troll-flip-backwards") + " " + Constants.getFeatureStatus(Constants.STATUS_FLIP_BACKWARDS), langConfig.getString("troll-flip-backwards-description")));
        Constants.TROLL_GUI.setItem(20, ItemBuilder.createItemWithLore(Material.WRITABLE_BOOK, ChatColor.WHITE + langConfig.getString("troll-spam-messages") + " " + Constants.getFeatureStatus(Constants.STATUS_SPAM_MESSAGES), langConfig.getString("troll-spam-messages-description")));
        Constants.TROLL_GUI.setItem(22, ItemBuilder.createItemWithLore(Material.NOTE_BLOCK, ChatColor.WHITE + langConfig.getString("troll-spam-sounds") + " " + Constants.getFeatureStatus(Constants.STATUS_SPAM_SOUNDS), langConfig.getString("troll-spam-sounds-description")));
        Constants.TROLL_GUI.setItem(24, ItemBuilder.createItemWithLore(Material.TRIPWIRE_HOOK, ChatColor.WHITE + langConfig.getString("troll-semi-ban") + " " + Constants.getFeatureStatus(Constants.STATUS_SEMI_BAN), langConfig.getString("troll-semi-ban-description")));
        Constants.TROLL_GUI.setItem(28, ItemBuilder.createItemWithLore(Material.TNT, ChatColor.WHITE + langConfig.getString("troll-tnt-track") + " " + Constants.getFeatureStatus(Constants.STATUS_TNT_TRACK), langConfig.getString("troll-tnt-track-description")));
        Constants.TROLL_GUI.setItem(30, ItemBuilder.createItemWithLore(Material.SPAWNER, ChatColor.WHITE + langConfig.getString("troll-mob-spawner") + " " + Constants.getFeatureStatus(Constants.STATUS_MOB_SPAWNER), langConfig.getString("troll-mob-spawner-description")));
        Constants.TROLL_GUI.setItem(32, ItemBuilder.createItemWithLore(Material.SKELETON_SKULL, ChatColor.WHITE + langConfig.getString("troll-slowly-kill") + " " + Constants.getFeatureStatus(Constants.STATUS_SLOWLY_KILL), langConfig.getString("troll-slowly-kill-description")));
        Constants.TROLL_GUI.setItem(34, ItemBuilder.createItemWithLore(Material.MUSIC_DISC_11, ChatColor.WHITE + langConfig.getString("troll-random-scary-sound"), langConfig.getString("troll-random-scary-sound-description")));
        Constants.TROLL_GUI.setItem(36, ItemBuilder.createItemWithLore(Material.EGG, ChatColor.WHITE + langConfig.getString("troll-inventory-drop"), langConfig.getString("troll-inventory-drop-description")));
        Constants.TROLL_GUI.setItem(38, ItemBuilder.createItemWithLore(Material.FIREWORK_ROCKET, ChatColor.WHITE + langConfig.getString("troll-rocket"), langConfig.getString("troll-rocket-description")));
        Constants.TROLL_GUI.setItem(40, ItemBuilder.createItemWithLore(Material.PAPER, ChatColor.WHITE + langConfig.getString("troll-fake-ban"), langConfig.getString("troll-fake-ban-description")));
        Constants.TROLL_GUI.setItem(42, ItemBuilder.createItemWithLore(Material.ENCHANTED_GOLDEN_APPLE, ChatColor.WHITE + langConfig.getString("troll-fake-op"), langConfig.getString("troll-fake-op-description")));

        // Placeholders
        byte[] placeholderArray = new byte[]{0, 1, 2, 3, 5, 6, 7, 8, 45, 46, 52};
        for (byte slot : placeholderArray) {
            Constants.TROLL_GUI.setItem(slot, ItemBuilder.createItemWithLore(Material.RED_STAINED_GLASS_PANE, " ", langConfig.getString("gui-placeholder-description")));
        }

        // Placeholders
        byte[] placeholderArray2 = new byte[]{9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 33, 35, 37, 39, 41, 43};
        for (byte slot : placeholderArray2) {
            Constants.TROLL_GUI.setItem(slot, ItemBuilder.createItemWithLore(Material.GRAY_STAINED_GLASS_PANE, " ", langConfig.getString("gui-placeholder-description")));
        }

        // Placeholders coming soon
        byte[] placeholderArray3 = new byte[]{18, 26, 44};
        for (byte slot : placeholderArray3) {
            Constants.TROLL_GUI.setItem(slot, ItemBuilder.createItemWithLore(Material.REDSTONE, langConfig.getString("gui-placeholder-coming-soon"), langConfig.getString("gui-placeholder-coming-soon-description")));
        }

        player.openInventory(Constants.TROLL_GUI);

        return true;
    }
}