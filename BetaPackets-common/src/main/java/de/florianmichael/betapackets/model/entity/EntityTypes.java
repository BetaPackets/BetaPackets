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

package de.florianmichael.betapackets.model.entity;

import de.florianmichael.betapackets.model.base.ProtocolCollection;

public enum EntityTypes {

    // v1.8
    ITEM("Item", 1),
    XP_ORB("XPOrb", 2),
    THROWN_EGG("ThrownEgg", 7),
    LEASH_KNOT("LeashKnot", 8),
    PAINTING("Painting", 9),
    ARROW("Arrow", 10),
    SNOWBALL("Snowball", 11),
    FIREBALL("Fireball", 12),
    SMALL_FIREBALL("SmallFireball", 13),
    ENDER_PEARL("ThrownEnderpearl", 14),
    ENDER_SIGNAL("EyeOfEnderSignal", 15),
    THROWN_POTION("ThrownPotion", 16),
    THROWN_EXP_BOTTLE("ThrownExpBottle", 17),
    ITEM_FRAME("ItemFrame", 18),
    WITHER_SKULL("WitherSkull", 19),
    PRIMED_TNT("PrimedTnt", 20),
    FALLING_BLOCK("FallingSand", 21),
    FIREWORK("FireworksRocketEntity", 22),
    ARMOR_STAND("ArmorStand", 30),
    BOAT("Boat", 41),
    MINECART("MinecartRideable", 42),
    MINECART_CHEST("MinecartChest", 43),
    MINECART_FURNACE("MinecartFurnace", 44),
    MINECART_TNT("MinecartTNT", 45),
    MINECART_HOPPER("MinecartHopper", 46),
    MINECART_MOB_SPAWNER("MinecartMobSpawner", 47),
    MINECART_COMMAND("MinecartCommandBlock", 40),
    MOB("Mob", 48),
    MONSTER("Monster", 49),
    CREEPER("Creeper", 50),
    SKELETON("Skeleton", 51),
    SPIDER("Spider", 52),
    GIANT("Giant", 53),
    ZOMBIE("Zombie", 54),
    SLIME("Slime", 55),
    GHAST("Ghast", 56),
    PIG_ZOMBIE("PigZombie", 57),
    ENDERMAN("Enderman", 58),
    CAVE_SPIDER("CaveSpider", 59),
    SILVERFISH("Silverfish", 60),
    BLAZE("Blaze", 61),
    MAGMA_CUBE("LavaSlime", 62),
    ENDER_DRAGON("EnderDragon", 63),
    WITHER("WitherBoss", 64),
    BAT("Bat", 65),
    WITCH("Witch", 66),
    ENDERMITE("Endermite", 67),
    GUARDIAN("Guardian", 68),
    PIG("Pig", 90),
    SHEEP("Sheep", 91),
    COW("Cow", 92),
    CHICKEN("Chicken", 93),
    SQUID("Squid", 94),
    WOLF("Wolf", 95),
    MUSHROOM_COW("MushroomCow", 96),
    SNOWMAN("SnowMan", 97),
    OCELOT("Ozelot", 98),
    IRON_GOLEM("VillagerGolem", 99),
    HORSE("EntityHorse", 100),
    RABBIT("Rabbit", 101),
    VILLAGER("Villager", 120),
    ENDER_CRYSTAL("EnderCrystal", 200),

    // v1.9
    AREA_EFFECT_CLOUD("AreaEffectCloud", -1, 3),
    SPECTRAL_ARROW("SpectralArrow", -1, 24),
    SHULKER_BULLET("ShulkerBullet", -1, 25),
    DRAGON_FIREBALL("DragonFireball", -1, 26),
    SHULKER("Shulker", -1, 69),
    ;

    public final String name;
    public final int v1_8Id;
    public final int v1_9Id;

    EntityTypes(String name, int v1_8Id) {
        this(name, v1_8Id, v1_8Id);
    }

    EntityTypes(String name, int v1_8Id, int v1_9Id) {
        this.name = name;
        this.v1_8Id = v1_8Id;
        this.v1_9Id = v1_9Id;
    }

    public static EntityTypes getById(final ProtocolCollection version, final byte id) {
        for (EntityTypes value : values()) {
            final int fieldId = value.getId(version);
            if (fieldId == -1) continue;
            if (fieldId == id) return value;
        }
        return null;
    }

    public int getId(final ProtocolCollection version) {
        if (version.isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            return v1_9Id;
        }
        return v1_8Id;
    }
}
