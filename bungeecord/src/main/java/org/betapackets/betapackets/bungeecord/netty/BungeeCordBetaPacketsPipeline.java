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

package org.betapackets.betapackets.bungeecord.netty;

import org.betapackets.betapackets.netty.BetaPacketsPipeline;
import org.betapackets.betapackets.netty.ViaVersionSupport;
import org.betapackets.betapackets.connection.UserConnection;
import io.netty.channel.ChannelHandlerContext;

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
    public String getPacketCompressName() {
        return "compress";
    }

    @Override
    public String getFrameEncoderName() {
        return "frame-prepender";
    }

    @Override
    public String getPacketDecoderName() {
        return ViaVersionSupport.getDecoderName("packet-decoder");
    }

    @Override
    public String getPacketEncoderName() {
        return ViaVersionSupport.getEncoderName("packet-encoder");
    }
}
