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

package org.betapackets.betapackets.model.block.properties.enums;

public enum Shape {
    ASCENDING_EAST, ASCENDING_NORTH, ASCENDING_SOUTH, ASCENDING_WEST, EAST_WEST, INNER_LEFT, INNER_RIGHT, NORTH_EAST,
    NORTH_SOUTH, NORTH_WEST, OUTER_LEFT, OUTER_RIGHT, SOUTH_EAST, SOUTH_WEST, STRAIGHT;

    public static Shape getByName(String name) {
        return valueOf(name.toUpperCase());
    }
}