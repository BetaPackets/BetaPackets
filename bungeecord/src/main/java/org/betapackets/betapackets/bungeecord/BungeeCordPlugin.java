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

package org.betapackets.betapackets.bungeecord;

import org.betapackets.betapackets.BetaPacketsPlatform;
import org.betapackets.betapackets.bungeecord.util.TheUnsafeReflect;
import org.betapackets.betapackets.bungeecord.netty.BetaPacketsKickStringWriter;
import org.betapackets.betapackets.bungeecord.util.JLoggerToSLF4J;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.UUID;
import java.util.logging.Logger;

public class BungeeCordPlugin extends Plugin implements BetaPacketsPlatform<ProxiedPlayer> {

    private final static Logger logging = new JLoggerToSLF4J(LoggerFactory.getLogger("BetaPackets (BungeeCord)"));

    @Override
    public void onLoad() {
        this.loadPlatform();
        TheUnsafeReflect.setKickStringWriter(new BetaPacketsKickStringWriter());
    }

    @Override
    public void onDisable() {
        this.unloadPlatform();
    }

    @Override
    public String getPlatformName() {
        return "BungeeCord";
    }

    @Override
    public ProxiedPlayer getPlayer(UUID uuid) {
        return getProxy().getPlayer(uuid);
    }

    @Override
    public InputStream getResource(String path) {
        return getResourceAsStream(path);
    }

    @Override
    public Object getPlugin() {
        return this;
    }

    @Override
    public boolean isPluginLoaded(String name) {
        return getProxy().getPluginManager().getPlugin(name) != null;
    }

    @Override
    public void kickAll(String message) {
        for (ProxiedPlayer player : getProxy().getPlayers()) {
            player.disconnect(new TextComponent(message));
        }
    }

    @Override
    public Logger getLogging() {
        return logging;
    }
}
