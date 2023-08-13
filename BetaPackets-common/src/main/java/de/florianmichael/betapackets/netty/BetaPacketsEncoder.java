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

package de.florianmichael.betapackets.netty;

import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class BetaPacketsEncoder extends MessageToByteEncoder<FunctionalByteBuf> {

    private String compressHandler;

    public BetaPacketsEncoder(String compressHandler) {
        this.compressHandler = compressHandler;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, FunctionalByteBuf msg, ByteBuf out) throws Exception {
        out.writeBytes(msg.getBuffer());
        // TODO compression handling here
    }
}
