/*
 * This file is part of BetaPackets - https://github.com/BetaPackets/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD
 * Copyright (C) 2023 DietrichPaul and contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.betapackets.betapackets.spigot;

import org.betapackets.betapackets.BetaPacketsPlatform;
import org.betapackets.betapackets.spigot.netty.BukkitInjector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.logging.Logger;

public class SpigotPlugin extends JavaPlugin implements BetaPacketsPlatform<Player> {

    private Logger logging;

    @Override
    public void onEnable() {
        logging = Logger.getLogger("BetaPackets (Spigot)");
        this.loadPlatform();
        BukkitInjector.inject();
    }

    @Override
    public void onDisable() {
        this.unloadPlatform();
    }

    @Override
    public Logger getLogging() {
        return logging;
    }

    @Override
    public String getPlatformName() {
        return "Spigot";
    }

    @Override
    public Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public Object getPlugin() {
        return this;
    }

    @Override
    public boolean isPluginLoaded(String name) {
        return getServer().getPluginManager().isPluginEnabled(name);
    }

    @Override
    public void kickAll(String message) {
        for (Player onlinePlayer : getServer().getOnlinePlayers()) {
            onlinePlayer.kickPlayer(message);
        }
    }
}
