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

package de.florianmichael.betapackets.bungeecord.injection;

import de.florianmichael.betapackets.BetaPackets;
import net.md_5.bungee.protocol.KickStringWriter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectionInject {

    private static Field pipelineUtilsKickStringWriter;

    static {
        try {
            final Class<?> pipelineUtils = Class.forName("net.md_5.bungee.netty.PipelineUtils");
            pipelineUtilsKickStringWriter = pipelineUtils.getDeclaredField("legacyKicker");
            pipelineUtilsKickStringWriter.setAccessible(true);
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setKickStringWriter(KickStringWriter writer) {
        try {
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(pipelineUtilsKickStringWriter, pipelineUtilsKickStringWriter.getModifiers() & ~Modifier.FINAL);

            pipelineUtilsKickStringWriter.set(null, writer);
            modifiersField.setAccessible(false);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
