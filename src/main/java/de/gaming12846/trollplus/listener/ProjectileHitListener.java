/*
 * This file is part of TrollPlus.
 * Copyright (C) 2024 Gaming12846
 */

package de.gaming12846.trollplus.listener;

import de.gaming12846.trollplus.TrollPlus;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class ProjectileHitListener implements Listener {
    private final TrollPlus plugin;

    public ProjectileHitListener(TrollPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onProjectileHitEvent(ProjectileHitEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        if (!(event.getEntity() instanceof Arrow)) return;

        Arrow arrow = (Arrow) event.getEntity();

        // Explosion bow
        if (arrow.hasMetadata("TROLLPLUS_EXPLOSION_ARROW")) {
            arrow.removeMetadata("TROLLPLUS_EXPLOSION_ARROW", plugin);

            Location location = arrow.getLocation();
            arrow.getWorld().createExplosion(location.getX(), location.getY(), location.getZ(), 2, plugin.getConfig().getBoolean("set-fire"), plugin.getConfig().getBoolean("break-blocks"));
        }

        // TNT bow
        if (arrow.hasMetadata("TROLLPLUS_TNT_ARROW")) {
            arrow.removeMetadata("TROLLPLUS_TNT_ARROW", plugin);

            TNTPrimed tnt = arrow.getWorld().spawn(arrow.getLocation(), TNTPrimed.class);
            tnt.setFuseTicks(40);
            tnt.setMetadata("TROLLPLUS_TNT", new FixedMetadataValue(plugin, tnt));
            tnt.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_TNT_PRIMED, 20, 1);
        }

        // Lightning bolt bow
        if (arrow.hasMetadata("TROLLPLUS_LIGHTNING_BOLT_ARROW")) {
            arrow.removeMetadata("TROLLPLUS_LIGHTNING_BOLT_ARROW", plugin);

            LightningStrike lightningStrike = arrow.getWorld().strikeLightning(arrow.getLocation());
            lightningStrike.setMetadata("TROLLPLUS_LIGHTNING_BOLT", new FixedMetadataValue(plugin, lightningStrike));
        }

        // Silverfish bow
        if (arrow.hasMetadata("TROLLPLUS_SILVERFISH_ARROW")) {
            arrow.removeMetadata("TROLLPLUS_SILVERFISH_ARROW", plugin);

            arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_SILVERFISH_STEP, 20, 1);
            // EXPLOSION_LARGE was removed in 1.21
            arrow.getWorld().spawnParticle(Particle.EXPLOSION, arrow.getLocation(), 1);

            for (byte i = 0; i < RandomUtils.nextInt(3, 5); i++) {
                arrow.getWorld().spawnEntity(arrow.getLocation().add(RandomUtils.nextInt(0, 2), RandomUtils.nextInt(0, 2), RandomUtils.nextInt(0, 2)), EntityType.SILVERFISH);
            }
        }
    }
}