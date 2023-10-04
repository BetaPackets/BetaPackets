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

package org.betapackets.betapackets.packet.model.s2c.login;

import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.model.auth.GameProfile;
import org.betapackets.betapackets.model.auth.ProfileProperty;
import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;
import org.betapackets.betapackets.packet.type.PacketType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WrapperLoginServerLoginSuccess extends PacketWrapper<WrapperLoginServerLoginSuccess> {

    private GameProfile profile;

    public WrapperLoginServerLoginSuccess(PacketEvent event) throws IOException {
        super(event);
    }

    public WrapperLoginServerLoginSuccess(GameProfile profile) {
        super(PacketType.Login.Server.LOGIN_SUCCESS);
        this.profile = profile;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeUUID(profile.uuid);
        buf.writeString(profile.name);
        buf.writeVarInt(profile.profileProperties.size());
        for (ProfileProperty property : profile.profileProperties) {
            buf.writeString(property.name);
            buf.writeString(property.value);
            if (property.signature != null) {
                buf.writeBoolean(true);
                buf.writeString(property.signature);
            } else {
                buf.writeBoolean(false);
            }
        }
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        UUID uuid = buf.readUUID();
        String name = buf.readString(16);

        int propertyAmount = buf.readVarInt();
        List<ProfileProperty> properties = new ArrayList<>(propertyAmount);
        for (int i = 0; i < propertyAmount; i++) {
            String propertyName = buf.readString();
            String propertyValue = buf.readString();
            String signature = buf.readBoolean() ? buf.readString() : null;
            properties.add(new ProfileProperty(propertyName, propertyValue, signature));
        }
        profile = new GameProfile(uuid, name, properties);
    }

    @Override
    public void copyFrom(WrapperLoginServerLoginSuccess base) {
        profile = base.profile;
    }

    public GameProfile getProfile() {
        return profile;
    }

    public void setProfile(GameProfile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "WrapperServerLoginSuccess{" +
                "profile=" + profile +
                '}';
    }
}
