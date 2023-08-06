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

package de.florianmichael.betapackets.bungeecord.util;

import de.florianmichael.betapackets.BetaPackets;
import sun.misc.Unsafe;
import net.md_5.bungee.protocol.KickStringWriter;

import java.lang.reflect.Field;

public class TheUnsafeReflect {
    private final static Unsafe jvmInternal = getTheUnsafe();
    private static Field pipelineUtilsKickStringWriter;

    static {
        try {
            final Class<?> pipelineUtils = Class.forName("net.md_5.bungee.netty.PipelineUtils");

            pipelineUtilsKickStringWriter = pipelineUtils.getDeclaredField("legacyKicker");
        } catch (NoSuchFieldException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Unsafe getTheUnsafe() {
        try {
            for (Field field : Unsafe.class.getDeclaredFields()) {
                if (field.getType() == Unsafe.class) {
                    field.setAccessible(true);
                    return (Unsafe) field.get(null);
                }
            }
        } catch (IllegalAccessException ignored) {
            BetaPackets.getPlatform().getLogging().severe("Could not get Unsafe instance!");
        }
        return null;
    }

    public static void setKickStringWriter(KickStringWriter writer) {
        jvmInternal.putObject(pipelineUtilsKickStringWriter.getDeclaringClass(), jvmInternal.staticFieldOffset(pipelineUtilsKickStringWriter), writer);
    }
}
