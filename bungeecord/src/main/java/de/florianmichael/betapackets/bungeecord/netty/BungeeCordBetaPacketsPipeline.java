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

package de.florianmichael.betapackets.bungeecord.netty;

import de.florianmichael.betapackets.base.api.UserConnection;
import de.florianmichael.betapackets.netty.BetaPacketsPipeline;
import de.florianmichael.betapackets.netty.event.ReorderPipelineEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class BungeeCordBetaPacketsPipeline extends BetaPacketsPipeline {

    public BungeeCordBetaPacketsPipeline(UserConnection userConnection) {
        super(userConnection);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);

        addAutomaticallyReorderElement(ctx.pipeline());
    }

    @Override
    public String getPacketDecompressName() {
        return "decompress";
    }

    @Override
    public String getPacketCompressName() {
        return "compress";
    }

    @Override
    public String getPacketDecoderName() {
        return "packet-decoder";
    }

    @Override
    public String getPacketEncoderName() {
        return "packet-encoder";
    }
}
