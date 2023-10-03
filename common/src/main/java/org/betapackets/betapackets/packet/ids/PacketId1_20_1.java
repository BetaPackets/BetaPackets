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

package org.betapackets.betapackets.packet.ids;

import org.betapackets.betapackets.packet.type.PacketType;

public class PacketId1_20_1 extends PacketIdBase1_7 {

    public PacketId1_20_1() {
        // s -> c
        registerPlayPacket(0x00, PacketType.Play.Server.BUNDLE_DELIMITER);
        registerPlayPacket(0x01, PacketType.Play.Server.SPAWN_ENTITY);
        registerPlayPacket(0x02, PacketType.Play.Server.SPAWN_EXPERIENCE_ORB);
        registerPlayPacket(0x03, PacketType.Play.Server.SPAWN_PLAYER);
        registerPlayPacket(0x04, PacketType.Play.Server.ENTITY_ANIMATION);
        registerPlayPacket(0x05, PacketType.Play.Server.AWARD_STATISTICS);
        registerPlayPacket(0x06, PacketType.Play.Server.ACKNOWLEDGE_BLOCK_CHANGE);
        registerPlayPacket(0x07, PacketType.Play.Server.SET_BLOCK_DESTROY_STAGE);
        registerPlayPacket(0x08, PacketType.Play.Server.BLOCK_ENTITY_DATA);
        registerPlayPacket(0x09, PacketType.Play.Server.BLOCK_ACTION);
        registerPlayPacket(0x0a, PacketType.Play.Server.BLOCK_UPDATE);
        registerPlayPacket(0x0b, PacketType.Play.Server.BOSS_BAR);
        registerPlayPacket(0x0c, PacketType.Play.Server.CHANGE_DIFFICULTY);
        registerPlayPacket(0x0d, PacketType.Play.Server.CHUNK_BIOMES);
        registerPlayPacket(0x0e, PacketType.Play.Server.CLEAR_TITLES);
        registerPlayPacket(0x0f, PacketType.Play.Server.COMMAND_SUGGESTION_RESPONSE);
        registerPlayPacket(0x10, PacketType.Play.Server.COMMANDS);
        registerPlayPacket(0x11, PacketType.Play.Server.CLOSE_CONTAINER);
        registerPlayPacket(0x12, PacketType.Play.Server.SET_CONTAINER_CONTENT);
        registerPlayPacket(0x13, PacketType.Play.Server.SET_CONTAINER_PROPERTY);
        registerPlayPacket(0x14, PacketType.Play.Server.SET_CONTAINER_SLOT);
        registerPlayPacket(0x15, PacketType.Play.Server.SET_COOLDOWN);
        registerPlayPacket(0x16, PacketType.Play.Server.CHAT_SUGGESTIONS);
        registerPlayPacket(0x17, PacketType.Play.Server.PLUGIN_MESSAGE);
        registerPlayPacket(0x18, PacketType.Play.Server.DAMAGE_EVENT);
        registerPlayPacket(0x19, PacketType.Play.Server.DELETE_MESSAGE);
        registerPlayPacket(0x1a, PacketType.Play.Server.DISCONNECT);
        registerPlayPacket(0x1b, PacketType.Play.Server.DISGUISED_CHAT_MESSAGE);
        registerPlayPacket(0x1c, PacketType.Play.Server.ENTITY_EVENT);
        registerPlayPacket(0x1d, PacketType.Play.Server.EXPLOSION);
        registerPlayPacket(0x1e, PacketType.Play.Server.UNLOAD_CHUNK);
        registerPlayPacket(0x1f, PacketType.Play.Server.GAME_EVENT);
        registerPlayPacket(0x20, PacketType.Play.Server.OPEN_HORSE_SCREEN);
        registerPlayPacket(0x21, PacketType.Play.Server.HURT_ANIMATION);
        registerPlayPacket(0x22, PacketType.Play.Server.INITIALIZE_WORLD_BORDER);
        registerPlayPacket(0x23, PacketType.Play.Server.KEEP_ALIVE);
        registerPlayPacket(0x24, PacketType.Play.Server.CHUNK_DATA);
        registerPlayPacket(0x25, PacketType.Play.Server.WORLD_EVENT);
        registerPlayPacket(0x26, PacketType.Play.Server.PARTICLE);
        registerPlayPacket(0x27, PacketType.Play.Server.UPDATE_LIGHT);
        registerPlayPacket(0x28, PacketType.Play.Server.JOIN_GAME);
        registerPlayPacket(0x29, PacketType.Play.Server.MAP_DATA);
        registerPlayPacket(0x2a, PacketType.Play.Server.MERCHANT_OFFERS);
        registerPlayPacket(0x2b, PacketType.Play.Server.UPDATE_ENTITY_POSITION);
        registerPlayPacket(0x2c, PacketType.Play.Server.UPDATE_ENTITY_POSITION_AND_ROTATION);
        registerPlayPacket(0x2d, PacketType.Play.Server.UPDATE_ENTITY_ROTATION);
        registerPlayPacket(0x2e, PacketType.Play.Server.MOVE_VEHICLE);
        registerPlayPacket(0x2f, PacketType.Play.Server.OPEN_BOOK);
        registerPlayPacket(0x30, PacketType.Play.Server.OPEN_SCREEN);
        registerPlayPacket(0x31, PacketType.Play.Server.OPEN_SIGN_EDITOR);
        registerPlayPacket(0x32, PacketType.Play.Server.PING);
        registerPlayPacket(0x33, PacketType.Play.Server.PLACE_GHOST_RECIPE);
        registerPlayPacket(0x34, PacketType.Play.Server.PLAYER_ABILITIES);
        registerPlayPacket(0x35, PacketType.Play.Server.PLAYER_CHAT_MESSAGE);
        registerPlayPacket(0x36, PacketType.Play.Server.END_COMBAT);
        registerPlayPacket(0x37, PacketType.Play.Server.ENTER_COMBAT);
        registerPlayPacket(0x38, PacketType.Play.Server.COMBAT_DEATH);
        registerPlayPacket(0x39, PacketType.Play.Server.PLAYER_INFO_REMOVE);
        registerPlayPacket(0x3a, PacketType.Play.Server.PLAYER_INFO_UPDATE);
        registerPlayPacket(0x3b, PacketType.Play.Server.LOOK_AT);
//        registerPlayPacket(0x3c, PacketType.Play.Server.SYNC_PLAYER_POSITION);
        registerPlayPacket(0x3d, PacketType.Play.Server.UPDATE_RECIPE_BOOK);
        registerPlayPacket(0x3e, PacketType.Play.Server.REMOVE_ENTITIES);
        registerPlayPacket(0x3f, PacketType.Play.Server.REMOVE_ENTITY_EFFECT);
        registerPlayPacket(0x40, PacketType.Play.Server.RESOURCE_PACK);
        registerPlayPacket(0x41, PacketType.Play.Server.RESPAWN);
        registerPlayPacket(0x42, PacketType.Play.Server.SET_HEAD_ROTATION);
        registerPlayPacket(0x43, PacketType.Play.Server.UPDATE_SECTION_BLOCKS);
        registerPlayPacket(0x44, PacketType.Play.Server.SELECT_ADVANCEMENTS_TAB);
        registerPlayPacket(0x45, PacketType.Play.Server.SERVER_DATA);
        registerPlayPacket(0x46, PacketType.Play.Server.SET_ACTION_BAR_TEXT);
        registerPlayPacket(0x47, PacketType.Play.Server.SET_BORDER_CENTER);
        registerPlayPacket(0x48, PacketType.Play.Server.SET_BORDER_LERP_SIZE);
        registerPlayPacket(0x49, PacketType.Play.Server.SET_BORDER_SIZE);
        registerPlayPacket(0x4a, PacketType.Play.Server.SET_BORDER_WARNING_DELAY);
        registerPlayPacket(0x4b, PacketType.Play.Server.SET_BORDER_WARNING_DISTANCE);
        registerPlayPacket(0x4c, PacketType.Play.Server.SET_CAMERA);
        registerPlayPacket(0x4d, PacketType.Play.Server.SET_HELD_ITEM);
        registerPlayPacket(0x4e, PacketType.Play.Server.SET_CENTER_CHUNK);
        registerPlayPacket(0x4f, PacketType.Play.Server.SET_RENDER_DISTANCE);
        registerPlayPacket(0x50, PacketType.Play.Server.SET_DEFAULT_SPAWN_POSITION);
        registerPlayPacket(0x51, PacketType.Play.Server.DISPLAY_OBJECTIVE);
        registerPlayPacket(0x52, PacketType.Play.Server.SET_ENTITY_METADATA);
        registerPlayPacket(0x53, PacketType.Play.Server.LINK_ENTITIES);
        registerPlayPacket(0x54, PacketType.Play.Server.SET_ENTITY_VELOCITY);
        registerPlayPacket(0x55, PacketType.Play.Server.SET_EQUIPMENT);
        registerPlayPacket(0x56, PacketType.Play.Server.SET_EXPERIENCE);
        registerPlayPacket(0x57, PacketType.Play.Server.SET_HEALTH);
        registerPlayPacket(0x58, PacketType.Play.Server.UPDATE_OBJECTIVES);
        registerPlayPacket(0x59, PacketType.Play.Server.SET_PASSENGERS);
        registerPlayPacket(0x5a, PacketType.Play.Server.UPDATE_TEAMS);
        registerPlayPacket(0x5b, PacketType.Play.Server.UPDATE_SCORE);
        registerPlayPacket(0x5c, PacketType.Play.Server.SET_SIMULATION_DISTANCE);
        registerPlayPacket(0x5d, PacketType.Play.Server.SET_SUBTITLE_TEXT);
        registerPlayPacket(0x5e, PacketType.Play.Server.UPDATE_TIME);
        registerPlayPacket(0x5f, PacketType.Play.Server.SET_TITLE_TEXT);
        registerPlayPacket(0x60, PacketType.Play.Server.SET_TITLE_ANIMATION_TIMES);
        registerPlayPacket(0x61, PacketType.Play.Server.ENTITY_SOUND_EFFECT);
        registerPlayPacket(0x62, PacketType.Play.Server.SOUND_EFFECT);
        registerPlayPacket(0x63, PacketType.Play.Server.STOP_SOUND);
        registerPlayPacket(0x64, PacketType.Play.Server.SYSTEM_CHAT_MESSAGE);
        registerPlayPacket(0x65, PacketType.Play.Server.SET_TAB_LIST_HEADER_AND_FOOTER);
        registerPlayPacket(0x66, PacketType.Play.Server.TAG_QUERY_RESPONSE);
        registerPlayPacket(0x67, PacketType.Play.Server.PICKUP_ITEM);
        registerPlayPacket(0x68, PacketType.Play.Server.TELEPORT_ENTITY);
        registerPlayPacket(0x69, PacketType.Play.Server.UPDATE_ADVANCEMENTS);
        registerPlayPacket(0x6a, PacketType.Play.Server.UPDATE_ATTRIBUTES);
        registerPlayPacket(0x6b, PacketType.Play.Server.FEATURE_FLAGS);
        registerPlayPacket(0x6c, PacketType.Play.Server.ENTITY_EFFECT);
        registerPlayPacket(0x6d, PacketType.Play.Server.UPDATE_RECIPES);
        registerPlayPacket(0x6e, PacketType.Play.Server.UPDATE_TAGS);

        // c -> s
        registerPlayPacket(PacketType.Play.Client.CONFIRM_TELEPORT);
        registerPlayPacket(PacketType.Play.Client.QUERY_BLOCK_ENTITY_TAG);
        registerPlayPacket(PacketType.Play.Client.CHANGE_DIFFICULTY);
        registerPlayPacket(PacketType.Play.Client.MESSAGE_ACKNOWLEDGEMENT);
        registerPlayPacket(PacketType.Play.Client.CHAT_COMMAND);
        registerPlayPacket(PacketType.Play.Client.CHAT_MESSAGE);
        registerPlayPacket(PacketType.Play.Client.PLAYER_SESSION);
        registerPlayPacket(PacketType.Play.Client.CLIENT_COMMAND);
        registerPlayPacket(PacketType.Play.Client.CLIENT_INFORMATION);
        registerPlayPacket(PacketType.Play.Client.COMMAND_SUGGESTION_REQUEST);
        registerPlayPacket(PacketType.Play.Client.CLICK_CONTAINER_BUTTON);
        registerPlayPacket(PacketType.Play.Client.CLICK_CONTAINER);
        registerPlayPacket(PacketType.Play.Client.CLOSE_CONTAINER);
        registerPlayPacket(PacketType.Play.Client.PLUGIN_MESSAGE);
        registerPlayPacket(PacketType.Play.Client.EDIT_BOOK);
        registerPlayPacket(PacketType.Play.Client.QUERY_ENTITY_TAG);
        registerPlayPacket(PacketType.Play.Client.USE_ENTITY);
        registerPlayPacket(PacketType.Play.Client.JIGSAW_GENERATE);
        registerPlayPacket(PacketType.Play.Client.KEEP_ALIVE);
        registerPlayPacket(PacketType.Play.Client.LOCK_DIFFICULTY);
        registerPlayPacket(PacketType.Play.Client.POSITION);
        registerPlayPacket(PacketType.Play.Client.POSITION_LOOK);
        registerPlayPacket(PacketType.Play.Client.LOOK);
        registerPlayPacket(PacketType.Play.Client.FLYING);
        registerPlayPacket(PacketType.Play.Client.MOVE_VEHICLE);
        registerPlayPacket(PacketType.Play.Client.PADDLE_BOAT);
        registerPlayPacket(PacketType.Play.Client.PICK_ITEM);
        registerPlayPacket(PacketType.Play.Client.PLACE_RECIPE);
        registerPlayPacket(PacketType.Play.Client.PLAYER_ABILITIES);
        registerPlayPacket(PacketType.Play.Client.PLAYER_ACTION);
        registerPlayPacket(PacketType.Play.Client.PLAYER_COMMAND);
        registerPlayPacket(PacketType.Play.Client.PLAYER_INPUT);
        registerPlayPacket(PacketType.Play.Client.PONG);
        registerPlayPacket(PacketType.Play.Client.CHANGE_RECIPE_BOOK_SETTINGS);
        registerPlayPacket(PacketType.Play.Client.SET_SEEN_RECIPE);
        registerPlayPacket(PacketType.Play.Client.RENAME_ITEM);
        registerPlayPacket(PacketType.Play.Client.RESOURCE_PACK);
        registerPlayPacket(PacketType.Play.Client.SEEN_ADVANCEMENTS);
        registerPlayPacket(PacketType.Play.Client.SELECT_TRADE);
        registerPlayPacket(PacketType.Play.Client.SET_BEACON_EFFECT);
        registerPlayPacket(PacketType.Play.Client.SET_HELD_ITEM);
        registerPlayPacket(PacketType.Play.Client.PROGRAM_COMMAND_BLOCK);
        registerPlayPacket(PacketType.Play.Client.PROGRAM_COMMAND_BLOCK_MINECART);
        registerPlayPacket(PacketType.Play.Client.SET_CREATIVE_MODE_SLOT);
        registerPlayPacket(PacketType.Play.Client.PROGRAM_JIGSAW_BLOCK);
        registerPlayPacket(PacketType.Play.Client.PROGRAM_STRUCTURE_BLOCK);
        registerPlayPacket(PacketType.Play.Client.UPDATE_SIGN);
        registerPlayPacket(PacketType.Play.Client.SWING_ARM);
        registerPlayPacket(PacketType.Play.Client.TELEPORT_TO_ENTITY);
        registerPlayPacket(PacketType.Play.Client.USE_ITEM_ON);
        registerPlayPacket(PacketType.Play.Client.USE_ITEM);
    }
}
