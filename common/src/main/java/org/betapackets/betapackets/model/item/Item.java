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

package org.betapackets.betapackets.model.item;

import org.betapackets.betapackets.model.block.Block;

import java.util.Objects;

public class Item {

    private final int id;
    private final ItemType type;
    private final int maxStackSize;
    private final Block blockType;
    private final boolean edible;

    public Item(int id, ItemType type, int maxStackSize, Block blockType, boolean edible) {
        this.id = id;
        this.type = type;
        this.maxStackSize = maxStackSize;
        this.blockType = blockType;
        this.edible = edible;
    }

    public int getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public Block getBlockType() {
        return blockType;
    }

    public boolean isEdible() {
        return edible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id && maxStackSize == item.maxStackSize && edible == item.edible && type == item.type && Objects.equals(blockType, item.blockType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, maxStackSize, blockType, edible);
    }
}
