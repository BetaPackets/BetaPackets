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

package de.florianmichael.betapackets.model.world;

public enum BlockIDs {

    // v1.8
    AIR("air", 0),
    STONE("stone", 1),
    GRASS("grass", 2),
    DIRT("dirt", 3),
    COBBLESTONE("cobblestone", 4),
    PLANKS("planks", 5),
    SAPLING("sapling", 6),
    BEDROCK("bedrock", 7),
    FLOWING_WATER("flowing_water", 8),
    WATER("water", 9),
    FLOWING_LAVA("flowing_lava", 10),
    LAVA("lava", 11),
    SAND("sand", 12),
    GRAVEL("gravel", 13),
    GOLD_ORE("gold_ore", 14),
    IRON_ORE("iron_ore", 15),
    COAL_ORE("coal_ore", 16),
    LOG("log", 17),
    LEAVES("leaves", 18),
    SPONGE("sponge", 19),
    GLASS("glass", 20),
    LAPIS_ORE("lapis_ore", 21),
    LAPIS_BLOCK("lapis_block", 22),
    DISPENSER("dispenser", 23),
    SANDSTONE("sandstone", 24),
    NOTE_BLOCK("note_block", 25),
    BED_BLOCK("bed_block", 26),
    POWERED_RAIL("powered_rail", 27),
    DETECTOR_RAIL("detector_rail", 28),
    PISTON_STICKY_BASE("piston_sticky_base", 29),
    WEB("web", 30),
    TALLGRASS("tallgrass", 31),
    DEAD_BUSH("dead_bush", 32),
    PISTON("piston", 33),
    PISTON_HEAD("piston_head", 34),
    WOOL("wool", 35),
    PISTON_EXTENSION("piston_extension", 36),
    YELLOW_FLOWER("yellow_flower", 37),
    RED_ROSE("red_rose", 38),
    BROWN_MUSHROOM("brown_mushroom", 39),
    RED_MUSHROOM("red_mushroom", 40),
    GOLD_BLOCK("gold_block", 41),
    IRON_BLOCK("iron_block", 42),
    DOUBLE_STONE_STEP("double_stone_step", 43),
    STONE_STEP("stone_step", 44),
    BRICK("brick", 45),
    TNT("tnt", 46),
    BOOKSHELF("bookshelf", 47),
    MOSSY_COBBLESTONE("mossy_cobblestone", 48),
    OBSIDIAN("obsidian", 49),
    TORCH("torch", 50),
    FIRE("fire", 51),
    MOB_SPAWNER("mob_spawner", 52),
    OAK_STAIRS("oak_stairs", 53),
    CHEST("chest", 54),
    REDSTONE_WIRE("redstone_wire", 55),
    DIAMOND_ORE("diamond_ore", 56),
    DIAMOND_BLOCK("diamond_block", 57),
    WORKBENCH("workbench", 58),
    CROPS("crops", 59),
    SOIL("soil", 60),
    FURNACE("furnace", 61),
    BURNING_FURNACE("lit_furnace", 62),
    SIGN_POST("sign_post", 63),
    WOODEN_DOOR("wooden_door", 64),
    LADDER("ladder", 65),
    RAILS("rails", 66),
    COBBLESTONE_STAIRS("cobblestone_stairs", 67),
    WALL_SIGN("wall_sign", 68),
    LEVER("lever", 69),
    STONE_PLATE("stone_plate", 70),
    IRON_DOOR_BLOCK("iron_door_block", 71),
    WOOD_PLATE("wood_plate", 72),
    REDSTONE_ORE("redstone_ore", 73),
    GLOWING_REDSTONE_ORE("glowing_redstone_ore", 74),
    REDSTONE_TORCH_OFF("redstone_torch_off", 75),
    REDSTONE_TORCH_ON("redstone_torch_on", 76),
    STONE_BUTTON("stone_button", 77),
    SNOW("snow", 78),
    ICE("ice", 79),
    SNOW_BLOCK("snow_block", 80),
    CACTUS("cactus", 81),
    CLAY("clay", 82),
    SUGAR_CANE_BLOCK("sugar_cane_block", 83),
    JUKEBOX("jukebox", 84),
    FENCE("fence", 85),
    PUMPKIN("pumpkin", 86),
    NETHERRACK("netherrack", 87),
    SOUL_SAND("soul_sand", 88),
    GLOWSTONE("glowstone", 89),
    PORTAL("portal", 90),
    JACK_O_LANTERN("jack_o_lantern", 91),
    CAKE_BLOCK("cake_block", 92),
    DIODE_BLOCK_OFF("diode_block_off", 93),
    DIODE_BLOCK_ON("diode_block_on", 94),
    LOCKED_CHEST("locked_chest", 95),
    TRAP_DOOR("trap_door", 96),
    MONSTER_EGG("monster_egg", 97),
    SMOOTH_BRICK("smooth_brick", 98),
    BROWN_MUSHROOM_BLOCK("brown_mushroom_block", 99),
    RED_MUSHROOM_BLOCK("red_mushroom_block", 100),
    IRON_BARS("iron_bars", 101),
    GLASS_PANE("glass_pane", 102),
    MELON_BLOCK("melon_block", 103),
    PUMPKIN_STEM("pumpkin_stem", 104),
    MELON_STEM("melon_stem", 105),
    VINE("vine", 106),
    FENCE_GATE("fence_gate", 107),
    BRICK_STAIRS("brick_stairs", 108),
    SMOOTH_STAIRS("smooth_stairs", 109),
    MYCEL("mycel", 110),
    WATER_LILY("water_lily", 111),
    NETHER_BRICK("nether_brick", 112),
    NETHER_FENCE("nether_fence", 113),
    NETHER_BRICK_STAIRS("nether_brick_stairs", 114),
    NETHER_WARTS("nether_warts", 115),
    ENCHANTMENT_TABLE("enchantment_table", 116),
    BREWING_STAND("brewing_stand", 117),
    CAULDRON("cauldron", 118),
    ENDER_PORTAL("ender_portal", 119),
    ENDER_PORTAL_FRAME("ender_portal_frame", 120),
    ENDER_STONE("ender_stone", 121),
    DRAGON_EGG("dragon_egg", 122),
    REDSTONE_LAMP_OFF("redstone_lamp", 123),
    REDSTONE_LAMP_ON("lit_redstone_lamp", 124),
    WOOD_DOUBLE_STEP("wood_double_step", 125),
    WOOD_STEP("wood_step", 126),
    COCOA("cocoa", 127),
    SANDSTONE_STAIRS("sandstone_stairs", 128),
    EMERALD_ORE("emerald_ore", 129),
    ENDER_CHEST("ender_chest", 130),
    TRIPWIRE_HOOK("tripwire_hook", 131),
    TRIPWIRE("tripwire", 132),
    EMERALD_BLOCK("emerald_block", 133),
    SPRUCE_WOOD_STAIRS("spruce_wood_stairs", 134),
    BIRCH_WOOD_STAIRS("birch_wood_stairs", 135),
    JUNGLE_WOOD_STAIRS("jungle_wood_stairs", 136),
    COMMAND("command", 137),
    BEACON("beacon", 138),
    COBBLE_WALL("cobble_wall", 139),
    FLOWER_POT("flower_pot", 140),
    CARROT("carrot", 141),
    POTATO("potato", 142),
    WOODEN_BUTTON("wooden_button", 143),
    SKULL("skull", 144),
    ANVIL("anvil", 145),
    TRAPPED_CHEST("trapped_chest", 146),
    GOLD_PLATE("light_weighted_pressure_plate", 147),
    IRON_PLATE("heavy_weighted_pressure_plate", 148),
    UNPOWERED_COMPARATOR("unpowered_comparator", 149),
    POWERED_COMPARATOR("powered_comparator", 150),
    DAYLIGHT_DETECTOR("daylight_detector", 151),
    REDSTONE_BLOCK("redstone_block", 152),
    QUARTZ_ORE("quartz_ore", 153),
    HOPPER("hopper", 154),
    QUARTZ_BLOCK("quartz_block", 155),
    QUARTZ_STAIRS("quartz_stairs", 156),
    ACTIVATOR_RAIL("activator_rail", 157),
    DROPPER("dropper", 158),
    STAINED_HARDENED_CLAY("stained_hardened_clay", 159),
    STAINED_GLASS_PANE("stained_glass_pane", 160),
    LEAVES_2("leaves_2", 161),
    LOG_2("log_2", 162),
    ACACIA_STAIRS("acacia_stairs", 163),
    DARK_OAK_STAIRS("dark_oak_stairs", 164),
    SLIME_BLOCK("slime_block", 165),
    BARRIER("barrier", 166),
    IRON_TRAPDOOR("iron_trapdoor", 167),
    PRISMARINE("prismarine", 168),
    SEA_LANTERN("sea_lantern", 169),
    HAY_BLOCK("hay_block", 170),
    CARPET("carpet", 171),
    HARD_CLAY("hard_clay", 172),
    COAL_BLOCK("coal_block", 173),
    PACKED_ICE("packed_ice", 174),
    DOUBLE_PLANT("double_plant", 175),
    STANDING_BANNER("standing_banner", 176),
    WALL_BANNER("wall_banner", 177),
    DAYLIGHT_DETECTOR_INVERTED("daylight_detector_inverted", 178),
    RED_SANDSTONE("red_sandstone", 179),
    RED_SANDSTONE_STAIRS("red_sandstone_stairs", 180),
    DOUBLE_STONE_SLAB2("double_stone_slab2", 181),
    STONE_SLAB2("stone_slab2", 182),
    SPRUCE_FENCE_GATE("spruce_fence_gate", 183),
    BIRCH_FENCE_GATE("birch_fence_gate", 184),
    JUNGLE_FENCE_GATE("jungle_fence_gate", 185),
    DARK_OAK_FENCE_GATE("dark_oak_fence_gate", 186),
    ACACIA_FENCE_GATE("acacia_fence_gate", 187),
    SPRUCE_FENCE("spruce_fence", 188),
    BIRCH_FENCE("birch_fence", 189),
    JUNGLE_FENCE("jungle_fence", 190),
    DARK_OAK_FENCE("dark_oak_fence", 191),
    ACACIA_FENCE("acacia_fence", 192),
    SPRUCE_DOOR("spruce_door", 193),
    BIRCH_DOOR("birch_door", 194),
    JUNGLE_DOOR("jungle_door", 195),
    ACACIA_DOOR("acacia_door", 196),
    DARK_OAK_DOOR("dark_oak_door", 197),

    // v1.9
    END_CORUS("end_corus", 198),
    CHORUS_PLANT("chorus_plant", 199),
    CHORUS_FLOWER("chorus_flower", 200),
    PURPUR_BLOCK("purpur_block", 201),
    PURPUR_PILLAR("purpur_pillar", 202),
    PURPUR_STAIRS("purpur_stairs", 203),
    PURPUR_DOUBLE_SLAB("purpur_double_slab", 204),
    PURPUR_SLAB("purpur_slab", 205),
    END_BRICKS("end_bricks", 206),
    BEETROOT_BLOCK("beetroot_block", 207),
    GRASS_PATH("grass_path", 208),
    END_GATEWAY("end_gateway", 209),
    REPEATING_COMMAND_BLOCK("repeating_command_block", 210),
    CHAIN_COMMAND_BLOCK("chain_command_block", 211),
    FROSTED_ICE("frosted_ice", 212),
    STRUCTURE_BLOCK("structure_block", 255);

    public final String name;
    public final int v1_8Id;

    BlockIDs(String name, int v1_8Id) {
        this.name = name;
        this.v1_8Id = v1_8Id;
    }
}