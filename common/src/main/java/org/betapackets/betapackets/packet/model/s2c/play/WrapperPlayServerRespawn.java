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

package org.betapackets.betapackets.packet.model.s2c.play;

import org.betapackets.betapackets.event.PacketEvent;
import org.betapackets.betapackets.model.game.GameMode;
import org.betapackets.betapackets.model.position.GlobalPos;
import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.packet.model.PacketWrapper;
import org.betapackets.betapackets.packet.type.Packet;
import org.betapackets.betapackets.packet.type.PacketType;
import net.lenni0451.mcstructs.core.Identifier;

import java.io.IOException;
import java.util.Optional;

public class WrapperPlayServerRespawn extends PacketWrapper<WrapperPlayServerRespawn> {

    private Identifier dimensionType;
    private Identifier dimensionName;
    private long hashedSeed;
    private GameMode gameMode;
    private GameMode previousGameMode;
    private boolean debug;
    private boolean flat;
    private boolean keepAttributes;
    private boolean keepMetadata;
    private Optional<GlobalPos> deathLocation;
    private int portalCooldown;


    public WrapperPlayServerRespawn(Identifier dimensionType, Identifier dimensionName, long hashedSeed, GameMode gameMode, GameMode previousGameMode, boolean debug, boolean flat, boolean keepAttributes, boolean keepMetadata, Optional<GlobalPos> deathLocation, int portalCooldown) {
        super(PacketType.Play.Server.RESPAWN);
        this.dimensionType = dimensionType;
        this.dimensionName = dimensionName;
        this.hashedSeed = hashedSeed;
        this.gameMode = gameMode;
        this.previousGameMode = previousGameMode;
        this.debug = debug;
        this.flat = flat;
        this.keepAttributes = keepAttributes;
        this.keepMetadata = keepMetadata;
        this.deathLocation = deathLocation;
        this.portalCooldown = portalCooldown;
    }

    public WrapperPlayServerRespawn(PacketEvent event) throws IOException {
        super(event);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        dimensionType = buf.readIdentifier();
        dimensionName = buf.readIdentifier();
        hashedSeed = buf.readLong();
        gameMode = GameMode.getById(buf.getProtocolVersion(), buf.readUnsignedByte());
        previousGameMode = GameMode.getOrNull(buf.getProtocolVersion(), buf.readByte());
        debug = buf.readBoolean();
        flat = buf.readBoolean();

        byte dataFlags = buf.readByte();
        keepAttributes = (dataFlags & 0b01) != 0;
        keepMetadata = (dataFlags & 0b10) != 0;

        deathLocation = buf.readOptional(FunctionalByteBuf::readGlobalPos);
        portalCooldown = buf.readVarInt();
    }

    @Override
    public void copyFrom(WrapperPlayServerRespawn base) {
        dimensionType = base.dimensionType;
        dimensionName = base.dimensionName;
        hashedSeed = base.hashedSeed;
        gameMode = base.gameMode;
        previousGameMode = base.previousGameMode;
        debug = base.debug;
        flat = base.flat;
        keepAttributes = base.keepAttributes;
        keepMetadata = base.keepMetadata;
        deathLocation = base.deathLocation;
        portalCooldown = base.portalCooldown;
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeIdentifier(dimensionType);
        buf.writeIdentifier(dimensionName);
        buf.writeLong(hashedSeed);
        buf.writeByte(gameMode.getId());
        buf.writeByte(previousGameMode == null ? -1 : previousGameMode.getId());
        buf.writeBoolean(debug);
        buf.writeBoolean(flat);
        buf.writeByte((keepAttributes ? 0b01 : 0) | (keepMetadata ? 0b10 : 0));
        buf.writeOptional(deathLocation, FunctionalByteBuf::writeGlobalPos);
        buf.writeVarInt(portalCooldown);
    }

    public Identifier getDimensionType() {
        return dimensionType;
    }

    public void setDimensionType(Identifier dimensionType) {
        this.dimensionType = dimensionType;
    }

    public Identifier getDimensionName() {
        return dimensionName;
    }

    public void setDimensionName(Identifier dimensionName) {
        this.dimensionName = dimensionName;
    }

    public long getHashedSeed() {
        return hashedSeed;
    }

    public void setHashedSeed(long hashedSeed) {
        this.hashedSeed = hashedSeed;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameMode getPreviousGameMode() {
        return previousGameMode;
    }

    public void setPreviousGameMode(GameMode previousGameMode) {
        this.previousGameMode = previousGameMode;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isFlat() {
        return flat;
    }

    public void setFlat(boolean flat) {
        this.flat = flat;
    }

    public boolean isKeepAttributes() {
        return keepAttributes;
    }

    public void setKeepAttributes(boolean keepAttributes) {
        this.keepAttributes = keepAttributes;
    }

    public boolean isKeepMetadata() {
        return keepMetadata;
    }

    public void setKeepMetadata(boolean keepMetadata) {
        this.keepMetadata = keepMetadata;
    }

    public Optional<GlobalPos> getDeathLocation() {
        return deathLocation;
    }

    public void setDeathLocation(Optional<GlobalPos> deathLocation) {
        this.deathLocation = deathLocation;
    }

    public int getPortalCooldown() {
        return portalCooldown;
    }

    public void setPortalCooldown(int portalCooldown) {
        this.portalCooldown = portalCooldown;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerRespawn{" +
                "dimensionType=" + dimensionType +
                ", dimensionName=" + dimensionName +
                ", hashedSeed=" + hashedSeed +
                ", gameMode=" + gameMode +
                ", previousGameMode=" + previousGameMode +
                ", debug=" + debug +
                ", flat=" + flat +
                ", keepAttributes=" + keepAttributes +
                ", keepMetadata=" + keepMetadata +
                ", deathLocation=" + deathLocation +
                ", portalCooldown=" + portalCooldown +
                '}';
    }
}
