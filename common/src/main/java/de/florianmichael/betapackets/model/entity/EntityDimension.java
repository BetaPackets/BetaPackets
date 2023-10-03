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

import java.util.Objects;

public class EntityDimension {

    private final float width;
    private final float height;
    private final float eyeHeight;
    private final boolean fixed;

    public EntityDimension(float width, float height, float eyeHeight, boolean fixed) {
        this.width = width;
        this.height = height;
        this.eyeHeight = eyeHeight;
        this.fixed = fixed;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getEyeHeight() {
        return eyeHeight;
    }

    public boolean isFixed() {
        return fixed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityDimension that = (EntityDimension) o;
        return Float.compare(that.width, width) == 0 && Float.compare(that.height, height) == 0 && Float.compare(that.eyeHeight, eyeHeight) == 0 && fixed == that.fixed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, eyeHeight, fixed);
    }

    @Override
    public String toString() {
        return "EntityDimension{" +
                "width=" + width +
                ", height=" + height +
                ", eyeHeight=" + eyeHeight +
                ", fixed=" + fixed +
                '}';
    }
}
