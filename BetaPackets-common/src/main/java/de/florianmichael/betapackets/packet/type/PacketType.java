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

package de.florianmichael.betapackets.packet.type;

public class PacketType {

    /*
    Handshaking
     */
    public static class Handshaking {
        public enum Client implements ClientPacket {
            HANDSHAKE,
            LEGACY_STATUS
        }
    }

    /*
    Status
     */
    public static class Status {
        public enum Server implements ServerPacket {
            STATUS_RESPONSE,
            PING_RESPONSE
        }

        public enum Client implements ClientPacket {
            STATUS_REQUEST,
            PING_REQUEST
        }
    }

    /*
    Login
     */
    public static class Login {
        public enum Server implements ClientPacket {
            DISCONNECT,
            ENCRYPTION_REQUEST,
            LOGIN_SUCCESS,
            SET_COMPRESSION,
            PLUGIN_REQUEST
        }

        public enum Client implements ClientPacket {
            LOGIN_START,
            ENCRYPTION_RESPONSE,
            PLUGIN_RESPONSE
        }
    }

    /*
    Play
     */
    public static class Play {

        public enum Server implements ServerPacket {
            BUNDLE_DELIMITER,
            SPAWN_ENTITY,
            SPAWN_EXPERIENCE_ORB,
            SPAWN_PLAYER,
            ENTITY_ANIMATION,
            AWARD_STATISTICS,
            ACKNOWLEDGE_BLOCK_CHANGE,
            SET_BLOCK_DESTROY_STAGE,
            BLOCK_ENTITY_DATA,
            BLOCK_ACTION,
            BLOCK_UPDATE,
            BOSS_BAR,
            CHANGE_DIFFICULTY,
            CHUNK_BIOMES,
            CLEAR_TITLES,
            COMMAND_SUGGESTION_RESPONSE,
            COMMANDS,
            CLOSE_CONTAINER,
            SET_CONTAINER_CONTENT,
            SET_CONTAINER_PROPERTY,
            SET_CONTAINER_SLOT,
            SET_COOLDOWN,
            CHAT_SUGGESTIONS,
            PLUGIN_MESSAGE,
            DAMAGE_EVENT,
            DELETE_MESSAGE,
            DISCONNECT,
            DISGUISED_CHAT_MESSAGE,
            ENTITY_EVENT,
            EXPLOSION,
            UNLOAD_CHUNK,
            GAME_EVENT,
            OPEN_HORSE_SCREEN,
            HURT_ANIMATION,
            INITIALIZE_WORLD_BORDER,
            KEEP_ALIVE,
            CHUNK_DATA,
            WORLD_EVENT,
            PARTICLE,
            UPDATE_LIGHT,
            JOIN_GAME,
            MAP_DATA,
            MERCHANT_OFFERS,
            UPDATE_ENTITY_POSITION,
            UPDATE_ENTITY_POSITION_AND_ROTATION,
            UPDATE_ENTITY_ROTATION,
            MOVE_VEHICLE,
            OPEN_BOOK,
            OPEN_SCREEN,
            OPEN_SIGN_EDITOR,
            PING,
            PLACE_GHOST_RECIPE,
            PLAYER_ABILITIES,
            PLAYER_CHAT_MESSAGE,
            END_COMBAT,
            ENTER_COMBAT,
            COMBAT_DEATH,
            PLAYER_INFO_REMOVE,
            PLAYER_INFO_UPDATE,
            LOOK_AT,
            SYNC_PLAYER_POSITION,
            UPDATE_RECIPE_BOOK,
            REMOVE_ENTITIES,
            REMOVE_ENTITY_EFFECT,
            RESOURCE_PACK,
            RESPAWN,
            SET_HEAD_ROTATION,
            UPDATE_SECTION_BLOCKS,
            SELECT_ADVANCEMENTS_TAB,
            SERVER_DATA,
            SET_ACTION_BAR_TEXT,
            SET_BORDER_CENTER,
            SET_BORDER_LERP_SIZE,
            SET_BORDER_SIZE,
            SET_BORDER_WARNING_DELAY,
            SET_BORDER_WARNING_DISTANCE,
            SET_CAMERA,
            SET_HELD_ITEM,
            SET_CENTER_CHUNK,
            SET_RENDER_DISTANCE,
            SET_DEFAULT_SPAWN_POSITION,
            DISPLAY_OBJECTIVE,
            SET_ENTITY_METADATA,
            LINK_ENTITIES,
            SET_ENTITY_VELOCITY,
            SET_EQUIPMENT,
            SET_EXPERIENCE,
            SET_HEALTH,
            UPDATE_OBJECTIVES,
            SET_PASSENGERS,
            UPDATE_TEAMS,
            UPDATE_SCORE,
            SET_SIMULATION_DISTANCE,
            SET_SUBTITLE_TEXT,
            UPDATE_TIME,
            SET_TITLE_TEXT,
            SET_TITLE_ANIMATION_TIMES,
            ENTITY_SOUND_EFFECT,
            SOUND_EFFECT,
            STOP_SOUND,
            SYSTEM_CHAT_MESSAGE,
            SET_TAB_LIST_HEADER_AND_FOOTER,
            TAG_QUERY_RESPONSE,
            PICKUP_ITEM,
            TELEPORT_ENTITY,
            UPDATE_ADVANCEMENTS,
            UPDATE_ATTRIBUTES,
            FEATURE_FLAGS,
            ENTITY_EFFECT,
            UPDATE_RECIPES,
            UPDATE_TAGS
        }

        public enum Client implements ClientPacket {
            CONFIRM_TELEPORT,
            QUERY_BLOCK_ENTITY_TAG,
            CHANGE_DIFFICULTY,
            MESSAGE_ACKNOWLEDGEMENT,
            CHAT_COMMAND,
            CHAT_MESSAGE,
            PLAYER_SESSION,
            CLIENT_COMMAND,
            CLIENT_INFORMATION,
            COMMAND_SUGGESTION_REQUEST,
            CLICK_CONTAINER_BUTTON,
            CLICK_CONTAINER,
            CLOSE_CONTAINER,
            PLUGIN_MESSAGE,
            EDIT_BOOK,
            QUERY_ENTITY_TAG,
            INTERACT,
            JIGSAW_GENERATE,
            KEEP_ALIVE,
            LOCK_DIFFICULTY,
            POSITION,
            POSITION_LOOK,
            LOOK,
            FLYING,
            MOVE_VEHICLE,
            PADDLE_BOAT,
            PICK_ITEM,
            PLACE_RECIPE,
            PLAYER_ABILITIES,
            PLAYER_ACTION,
            PLAYER_COMMAND,
            PLAYER_INPUT,
            PONG,
            CHANGE_RECIPE_BOOK_SETTINGS,
            SET_SEEN_RECIPE,
            RENAME_ITEM,
            RESOURCE_PACK,
            SEEN_ADVANCEMENTS,
            SELECT_TRADE,
            SET_BEACON_EFFECT,
            SET_HELD_ITEM,
            PROGRAM_COMMAND_BLOCK,
            PROGRAM_COMMAND_BLOCK_MINECART,
            SET_CREATIVE_MODE_SLOT,
            PROGRAM_JIGSAW_BLOCK,
            PROGRAM_STRUCTURE_BLOCK,
            UPDATE_SIGN,
            SWING_ARM,
            TELEPORT_TO_ENTITY,
            USE_ITEM_ON,
            USE_ITEM
        }
    }
}
