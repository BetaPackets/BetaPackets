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

package de.florianmichael.betapackets.registry;

import de.florianmichael.betapackets.base.registry.PacketRegistry;
import de.florianmichael.betapackets.model.base.NetworkSide;
import de.florianmichael.betapackets.model.base.NetworkState;
import de.florianmichael.betapackets.packet.play.c2s.*;
import de.florianmichael.betapackets.packet.play.s2c.*;

public class PacketRegistry1_8 extends PacketRegistry {

    public PacketRegistry1_8() {
        super(NetworkState.PLAY);
    }

    @Override
    public void init() {
        // S -> C
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x00, KeepAliveS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x01, JoinGameS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x02, ChatMessageS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x03, TimeUpdateS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x04, EntityEquipmentS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x05, SpawnPositionS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x06, UpdateHealthS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x07, RespawnS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x08, PlayerPositionAndLookS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x09, HeldItemChangeS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0A, UseBedS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0B, AnimationS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0C, SpawnPlayerS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0D, CollectItemS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0E, SpawnObjectS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0F, SpawnMobS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x10, SpawnPaintingS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x11, SpawnExperienceOrbS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x12, EntityVelocityS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x13, DestroyEntitiesS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x14, EntityS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x15, EntityRelativeMoveS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x16, EntityLookS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x17, EntityLookAndRelativeMoveS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x18, EntityTeleportS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x19, EntityHeadLookS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1A, EntityStatusS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1B, AttachEntityS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1C, EntityMetadataS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1D, EntityEffectS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1E, RemoveEntityEffectS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1F, SetExperienceS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x20, EntityPropertiesS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x21, ChunksS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x22, MultiBlockChangeS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x23, BlockChangeS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x24, BlockActionS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x25, BlockBreakAnimationS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x26, MapChunkBulkS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x27, ExplosionS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x28, EffectS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x29, SoundEffectS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2A, ParticleS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2B, ChangeGameStateS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2C, SpawnGlobalEntityS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2D, OpenWindowS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2E, CloseWindowS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2F, SetSlotS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x30, WindowItemsS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x31, WindowPropertyS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x32, ConfirmTransactionS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x33, UpdateSignS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x34, MapS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x35, UpdateBlockEntityS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x36, OpenSignEditorS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x37, StatisticsS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x38, PlayerListItemS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x39, PlayerAbilitiesS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3A, TabCompleteS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3B, ScoreboardObjectiveS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3C, UpdateScoreS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3D, DisplayScoreboardS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3E, TeamsS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3F, PluginMessageS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x40, DisconnectS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x41, ServerDifficultyS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x42, CombatEventS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x43, CameraS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x44, WorldBorderS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x45, TitleS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x46, SetCompressionS2CPacket_P.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x47, PlayerListHeaderFooterS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x48, ResourcePackSendS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x49, UpdateEntityNBTS2CPacket.class);

        // C -> S
        this.registerPacket(NetworkSide.SERVERBOUND, 0x00, KeepAliveC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x01, ChatMessageC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x02, UseEntityC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x03, PlayerC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x04, PlayerPositionC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x05, PlayerLookC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x06, PlayerPositionAndLookC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x07, PlayerDiggingC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x08, PlayerBlockPlacementC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x09, HeldItemChangeC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0A, AnimationC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0B, EntityActionC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0C, SteerVehicleC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0D, CloseWindowC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0E, ClickWindowC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0F, ConfirmTransactionC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x10, CreativeInventoryActionC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x11, EnchantItemC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x12, UpdateSignC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x13, PlayerAbilitiesC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x14, TabCompleteC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x15, ClientSettingsC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x16, ClientStatusC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x17, PluginMessageC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x18, SpectateC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x19, ResourcePackStatusC2SPacket.class);
    }
}
