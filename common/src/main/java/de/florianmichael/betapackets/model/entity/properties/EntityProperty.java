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

package de.florianmichael.betapackets.model.entity.properties;

import java.util.List;

public class EntityProperty {

    public String key;
    public double value;
    public List<EntityPropertyModifier> entityPropertyModifiers;

    public EntityProperty(String key, double value, List<EntityPropertyModifier> entityPropertyModifiers) {
        this.key = key;
        this.value = value;
        this.entityPropertyModifiers = entityPropertyModifiers;
    }

    @Override
    public String toString() {
        return "EntityProperty{" +
                "key='" + key + '\'' +
                ", value=" + value +
                ", entityPropertyModifiers=" + entityPropertyModifiers +
                '}';
    }
}
