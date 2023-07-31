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

public class EntityDimension {

    private float width;
    private float height;
    private float eyeHeight;
    private boolean fixed;

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
}
