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

import de.florianmichael.betapackets.model.base.NetworkSide;
import de.florianmichael.betapackets.packet.play.c2s.*;
import de.florianmichael.betapackets.packet.play.c2s.v1_9.SteerBoatC2SPacket;
import de.florianmichael.betapackets.packet.play.c2s.v1_9.TeleportConfirmC2SPacket;
import de.florianmichael.betapackets.packet.play.c2s.v1_9.UseItemC2SPacket;
import de.florianmichael.betapackets.packet.play.c2s.v1_9.VehicleMoveC2SPacket;
import de.florianmichael.betapackets.packet.play.s2c.*;
import de.florianmichael.betapackets.packet.play.s2c.v1_9.*;

public class PacketRegistry1_9 extends PacketRegistry1_8 {

    @Override
    public void init() {
        super.init();
        // S -> C
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x00, SpawnObjectS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x01, SpawnExperienceOrbS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x02, SpawnGlobalEntityS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x03, SpawnMobS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x04, SpawnPaintingS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x05, SpawnPlayerS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x06, AnimationS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x07, StatisticsS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x08, BlockBreakAnimationS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x09, UpdateBlockEntityS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0A, BlockActionS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0B, BlockChangeS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0C, BossBarS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0D, ServerDifficultyS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0E, TabCompleteS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x0F, ChatMessageS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x10, MultiBlockChangeS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x11, ConfirmTransactionS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x12, CloseWindowS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x13, OpenWindowS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x14, WindowItemsS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x15, WindowPropertyS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x16, SetSlotS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x17, SetCooldownS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x18, PluginMessageS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x19, SoundEffectS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1A, DisconnectS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1B, EntityStatusS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1C, ExplosionS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1D, UnloadChunkS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1E, ChangeGameStateS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x1F, KeepAliveC2SPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x20, ChunksS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x21, EffectS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x22, ParticleS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x23, JoinGameS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x24, MapS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x25, EntityRelativeMoveS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x26, EntityLookAndRelativeMoveS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x27, EntityLookS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x28, EntityS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x29, VehicleMoveS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2A, OpenSignEditorS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2B, PlayerAbilitiesC2SPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2C, CombatEventS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2D, PlayerListItemS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2E, PlayerPositionAndLookS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x2F, UseBedS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x30, DestroyEntitiesS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x31, RemoveEntityEffectS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x32, ResourcePackSendS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x33, RespawnS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x34, EntityHeadLookS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x35, WorldBorderS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x36, CameraS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x37, HeldItemChangeS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x38, DisplayScoreboardS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x39, EntityMetadataS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3A, AttachEntityS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3B, EntityVelocityS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3C, EntityEquipmentS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3D, SetExperienceS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3E, UpdateHealthS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x3F, ScoreboardObjectiveS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x40, SetPassengersS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x41, TeamsS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x42, UpdateScoreS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x43, SpawnPositionS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x44, TimeUpdateS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x46, UpdateSignS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x47, CustomSoundS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x48, PlayerListHeaderFooterS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x49, CollectItemS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x4A, EntityTeleportS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x4B, EntityPropertiesS2CPacket.class);
        this.registerPacket(NetworkSide.CLIENTBOUND, 0x4C, EntityEffectS2CPacket.class);

        // C -> S
        this.registerPacket(NetworkSide.SERVERBOUND, 0x00, TeleportConfirmC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x01, TabCompleteC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x02, ChatMessageC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x03, ClientStatusC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x04, ClientSettingsC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x05, ConfirmTransactionC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x06, EnchantItemC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x07, ClickWindowC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x08, CloseWindowC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x09, PluginMessageC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0A, UseEntityC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0B, KeepAliveC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0C, PlayerPositionC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0D, PlayerPositionAndLookC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0E, PlayerLookC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x0F, PlayerC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x10, VehicleMoveC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x11, SteerBoatC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x12, PlayerAbilitiesC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x13, PlayerDiggingC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x14, EntityActionC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x15, SteerVehicleC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x16, ResourcePackStatusC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x17, HeldItemChangeC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x18, CreativeInventoryActionC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x19, UpdateSignS2CPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x1A, AnimationC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x1B, SpectateC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x1C, PlayerBlockPlacementC2SPacket.class);
        this.registerPacket(NetworkSide.SERVERBOUND, 0x1D, UseItemC2SPacket.class);
    }
}
