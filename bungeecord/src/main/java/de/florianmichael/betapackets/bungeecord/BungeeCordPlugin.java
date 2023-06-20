/*
 * This file is part of BetaPackets - https://github.com/FlorianMichael/BetaPackets
 * Copyright (C) 2023 FlorianMichael/EnZaXD and contributors
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

package de.florianmichael.betapackets.bungeecord;

import de.florianmichael.betapackets.api.BetaPacketsAPIBase;
import de.florianmichael.betapackets.api.BetaPacketsPlatform;
import de.florianmichael.betapackets.bungeecord.injection.ReflectionInject;
import de.florianmichael.betapackets.bungeecord.netty.BetaPacketsKickStringWriter;
import de.florianmichael.betapackets.bungeecord.platform.BungeeCordAPIBase;
import de.florianmichael.betapackets.bungeecord.util.JLoggerToSLF4J;
import de.florianmichael.dietrichevents.DietrichEvents;
import net.md_5.bungee.api.plugin.Plugin;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class BungeeCordPlugin extends Plugin implements BetaPacketsPlatform {

    private final static Logger logging = new JLoggerToSLF4J(LoggerFactory.getLogger("BetaPackets (BungeeCord)"));
    private final static DietrichEvents events = DietrichEvents.createThreadSafe();

    @Override
    public void onLoad() {
        this.loadPlatform();
        ReflectionInject.setKickStringWriter(new BetaPacketsKickStringWriter());
    }

    @Override
    public String getPlatformName() {
        return "BungeeCord";
    }

    @Override
    public BetaPacketsAPIBase<?> getAPIBase() {
        return new BungeeCordAPIBase(getProxy());
    }

    @Override
    public DietrichEvents getEventProvider() {
        return events;
    }

    @Override
    public Logger getLogging() {
        return logging;
    }
}
