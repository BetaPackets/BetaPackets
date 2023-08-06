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

package de.florianmichael.betapackets.packet.model.s2c.play;

import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.event.PacketEvent;
import de.florianmichael.betapackets.model.game.GameMode;
import de.florianmichael.betapackets.model.position.GlobalPos;
import de.florianmichael.betapackets.packet.model.PacketWrapper;
import de.florianmichael.betapackets.packet.type.Packet;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WrapperPlayServerJoinGame extends PacketWrapper<WrapperPlayServerJoinGame> {

    private int entityId;
    private boolean hardcore;
    private GameMode gameMode;
    private GameMode previousGameMode;
    private List<Identifier> dimensionNames;
    private CompoundTag registryCodec;
    private Identifier dimensionType;
    private Identifier dimensionName;
    private long hashedSeed;
    private int maxPlayers;
    private int viewDistance;
    private int simulationDistance;
    private boolean reducedDebugInfo;
    private boolean respawnScreenEnabled;
    private boolean debug;
    private boolean flat;
    private Optional<GlobalPos> deathPosition;
    private int portalCooldown;

    public WrapperPlayServerJoinGame(PacketEvent event) throws IOException {
        super(event);
    }

    @Override
    public void write(Packet type, FunctionalByteBuf buf) throws IOException {
        buf.writeInt(entityId);
        buf.writeBoolean(hardcore);
        buf.writeByte(gameMode.getId());
        if (previousGameMode == null) buf.writeByte(-1);
        else buf.writeByte(previousGameMode.getId());
        buf.writeVarInt(dimensionNames.size());
        dimensionNames.forEach(buf::writeIdentifier);
        buf.writeCompoundTag(registryCodec);
        buf.writeIdentifier(dimensionType);
        buf.writeIdentifier(dimensionName);
        buf.writeLong(hashedSeed);
        buf.writeVarInt(maxPlayers);
        buf.writeVarInt(viewDistance);
        buf.writeVarInt(simulationDistance);
        buf.writeBoolean(reducedDebugInfo);
        buf.writeBoolean(respawnScreenEnabled);
        buf.writeBoolean(debug);
        buf.writeBoolean(flat);
        buf.writeOptional(deathPosition, FunctionalByteBuf::writeGlobalPos);
        buf.writeVarInt(portalCooldown);
    }

    @Override
    public void read(Packet type, FunctionalByteBuf buf) throws IOException {
        entityId = buf.readInt();
        hardcore = buf.readBoolean();
        gameMode = GameMode.getById(buf.getProtocolVersion(), buf.readUnsignedByte());

        byte previousGameModeByte = buf.readByte();
        if (previousGameModeByte == -1)
            previousGameMode = null;
        else previousGameMode = GameMode.getById(buf.getProtocolVersion(), previousGameModeByte);

        int dimensionCount = buf.readVarInt();
        dimensionNames = new ArrayList<>(dimensionCount);
        for (int i = 0; i < dimensionCount; i++) {
            dimensionNames.add(buf.readIdentifier());
        }

        registryCodec = buf.readCompoundTag();

        dimensionType = buf.readIdentifier();
        dimensionName = buf.readIdentifier();
        hashedSeed = buf.readLong();
        maxPlayers = buf.readVarInt();
        viewDistance = buf.readVarInt();
        simulationDistance = buf.readVarInt();
        reducedDebugInfo = buf.readBoolean();
        respawnScreenEnabled = buf.readBoolean();
        debug = buf.readBoolean();
        flat = buf.readBoolean();
        deathPosition = buf.readOptional(FunctionalByteBuf::readGlobalPos);
        portalCooldown = buf.readVarInt();
    }

    @Override
    public void copyFrom(WrapperPlayServerJoinGame base) {
        entityId = base.entityId;
        hardcore = base.hardcore;
        gameMode = base.gameMode;
        previousGameMode = base.previousGameMode;
        dimensionNames = base.dimensionNames;
        registryCodec = base.registryCodec;
        dimensionType = base.dimensionType;
        dimensionName = base.dimensionName;
        hashedSeed = base.hashedSeed;
        maxPlayers = base.maxPlayers;
        viewDistance = base.viewDistance;
        simulationDistance = base.simulationDistance;
        reducedDebugInfo = base.reducedDebugInfo;
        respawnScreenEnabled = base.respawnScreenEnabled;
        debug = base.debug;
        flat = base.flat;
        deathPosition = base.deathPosition;
        portalCooldown = base.portalCooldown;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public boolean isHardcore() {
        return hardcore;
    }

    public void setHardcore(boolean hardcore) {
        this.hardcore = hardcore;
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

    public List<Identifier> getDimensionNames() {
        return dimensionNames;
    }

    public void setDimensionNames(List<Identifier> dimensionNames) {
        this.dimensionNames = dimensionNames;
    }

    public CompoundTag getRegistryCodec() {
        return registryCodec;
    }

    public void setRegistryCodec(CompoundTag registryCodec) {
        this.registryCodec = registryCodec;
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

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getViewDistance() {
        return viewDistance;
    }

    public void setViewDistance(int viewDistance) {
        this.viewDistance = viewDistance;
    }

    public int getSimulationDistance() {
        return simulationDistance;
    }

    public void setSimulationDistance(int simulationDistance) {
        this.simulationDistance = simulationDistance;
    }

    public boolean isReducedDebugInfo() {
        return reducedDebugInfo;
    }

    public void setReducedDebugInfo(boolean reducedDebugInfo) {
        this.reducedDebugInfo = reducedDebugInfo;
    }

    public boolean isRespawnScreenEnabled() {
        return respawnScreenEnabled;
    }

    public void setRespawnScreenEnabled(boolean respawnScreenEnabled) {
        this.respawnScreenEnabled = respawnScreenEnabled;
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

    public Optional<GlobalPos> getDeathPosition() {
        return deathPosition;
    }

    public void setDeathPosition(Optional<GlobalPos> deathPosition) {
        this.deathPosition = deathPosition;
    }

    public int getPortalCooldown() {
        return portalCooldown;
    }

    public void setPortalCooldown(int portalCooldown) {
        this.portalCooldown = portalCooldown;
    }

    @Override
    public String toString() {
        return "WrapperPlayServerJoinGame{" +
                "entityId=" + entityId +
                ", hardcore=" + hardcore +
                ", gameMode=" + gameMode +
                ", previousGameMode=" + previousGameMode +
                ", dimensionNames=" + dimensionNames +
                ", registryCodec=" + registryCodec +
                ", dimensionType=" + dimensionType +
                ", dimensionName=" + dimensionName +
                ", hashedSeed=" + hashedSeed +
                ", maxPlayers=" + maxPlayers +
                ", viewDistance=" + viewDistance +
                ", simulationDistance=" + simulationDistance +
                ", reducedDebugInfo=" + reducedDebugInfo +
                ", respawnScreenEnabled=" + respawnScreenEnabled +
                ", debug=" + debug +
                ", flat=" + flat +
                ", deathPosition=" + deathPosition +
                ", portalCooldown=" + portalCooldown +
                '}';
    }
}
