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

package org.betapackets.betapackets.mapping;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.betapackets.betapackets.model.potioneffect.PotionEffect;
import org.betapackets.betapackets.model.potioneffect.PotionEffectType;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PotionEffectMapping {

    private final TIntObjectMap<PotionEffect> byId;
    private final Map<PotionEffectType, PotionEffect> byType;

    public static PotionEffectMapping loadJson(JsonArray array) {
        PotionEffectMapping mapping = new PotionEffectMapping(array.size());
        array.forEach(element -> {
            JsonObject potionEffectObject = element.getAsJsonObject();
            mapping.add(potionEffectObject.get("id").getAsInt(), PotionEffectType.valueOf(potionEffectObject.get("name").getAsString()));
        });
        return mapping;
    }

    public static PotionEffectMapping read(DataInputStream in) throws IOException {
        int length = in.readUnsignedByte();
        PotionEffectMapping mapping = new PotionEffectMapping(length);
        for (int i = 0; i < length; i++) {
            mapping.add(in.readUnsignedByte(), PotionEffectType.values()[in.readUnsignedByte()]);
        }
        return mapping;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeByte(byId.size());
        for (PotionEffect potionEffect : byType.values()) {
            out.writeByte(potionEffect.getId());
            out.writeByte(potionEffect.getType().ordinal());
        }
    }

    PotionEffectMapping(int size) {
        byId = new TIntObjectHashMap<>(size);
        byType = new HashMap<>(size);
    }

    public void add(int i, PotionEffectType type) {
        PotionEffect effect = new PotionEffect(i, type);
        byId.put(i, effect);
        byType.put(type, effect);
    }

    public PotionEffect getByType(PotionEffectType type) {
        return byType.get(type);
    }

    public PotionEffect getById(int id) {
        return byId.get(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PotionEffectMapping that = (PotionEffectMapping) o;
        return Objects.equals(byId, that.byId) && Objects.equals(byType, that.byType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(byId, byType);
    }
}
