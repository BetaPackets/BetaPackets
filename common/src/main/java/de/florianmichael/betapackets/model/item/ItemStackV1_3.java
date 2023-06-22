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

package de.florianmichael.betapackets.model.item;

import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.util.Objects;

public class ItemStackV1_3 {

    public int itemId;
    public int count;
    public int damage;
    public CompoundTag tag;

    public ItemStackV1_3(int itemId, int count, int damage, CompoundTag tag) {
        this.itemId = itemId;
        this.count = count;
        this.damage = damage;
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ItemStackV1_3{" +
                "itemId=" + itemId +
                ", count=" + count +
                ", damage=" + damage +
                ", tag='" + tag + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemStackV1_3 that = (ItemStackV1_3) o;

        if (itemId != that.itemId) return false;
        if (count != that.count) return false;
        if (damage != that.damage) return false;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        int result = itemId;
        result = 31 * result + count;
        result = 31 * result + damage;
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        return result;
    }
}
