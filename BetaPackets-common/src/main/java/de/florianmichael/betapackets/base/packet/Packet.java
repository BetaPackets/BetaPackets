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

package de.florianmichael.betapackets.base.packet;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

/**
 * This class is the base class for all packets.
 */
public abstract class Packet {

    /**
     * Writes the packet to the given {@link FunctionalByteBuf}.
     * @param buf the {@link FunctionalByteBuf} to write to
     * @throws Exception if an error occurs
     */
    public abstract void write(final FunctionalByteBuf buf) throws Exception;
}
