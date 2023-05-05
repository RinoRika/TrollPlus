/*
 * This file is part of TrollPlus.
 * Copyright (C) 2023 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import de.gaming12846.trollplus.utils.Constants;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChatListener implements Listener {
    private final TrollPlus plugin;

    public AsyncPlayerChatListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        // Feature control
        if (player.hasMetadata("TROLLPLUS_CONTROL_TARGET")) {
            if (!Constants.CONTROL_MESSAGE_BOOLEAN) event.setCancelled(true);
        }

        if (player.hasMetadata("TROLLPLUS_CONTROL_PLAYER")) {
            event.setCancelled(true);
            Constants.CONTROL_MESSAGE = event.getMessage();
            Constants.CONTROL_MESSAGE_BOOLEAN = true;
        }

        // Feature semi ban
        if (player.hasMetadata("TROLLPLUS_SEMI_BAN")) {
            event.setCancelled(true);

            String semiBanMessageReplace = plugin.getConfig().getString("semi-ban-message-replace", "");
            if (semiBanMessageReplace.isEmpty()) semiBanMessageReplace = "";

            if (!semiBanMessageReplace.isEmpty())
                player.sendMessage(semiBanMessageReplace.replace("[player]", player.getName()) + " " + event.getMessage());
        }
    }
}