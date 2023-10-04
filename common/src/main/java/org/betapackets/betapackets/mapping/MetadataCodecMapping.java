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
import org.betapackets.betapackets.model.entity.metadata.MetadataCodecType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MetadataCodecMapping {

    private final List<MetadataCodecType> types = new LinkedList<>();

    public static MetadataCodecMapping read(DataInputStream in) throws IOException {
        MetadataCodecMapping mapping = new MetadataCodecMapping();
        int length = in.readUnsignedByte();
        for (int i = 0; i < length; i++) {
            mapping.add(MetadataCodecType.values()[in.readUnsignedByte()]);
        }
        return mapping;
    }

    public static MetadataCodecMapping loadJson(JsonArray array) {
        MetadataCodecMapping mapping = new MetadataCodecMapping();
        array.forEach(element -> {
            mapping.add(MetadataCodecType.valueOf(element.getAsString()));
        });
        return mapping;
    }

    public void write(DataOutputStream out) throws IOException {
        out.writeByte(types.size());
        for (MetadataCodecType type : types) {
            out.writeByte(type.ordinal());
        }
    }

    public void add(MetadataCodecType type) {
        types.add(type);
    }

    public MetadataCodecType getCodecType(int id) {
        return types.get(id);
    }

    public int getId(MetadataCodecType type) {
        return types.indexOf(type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MetadataCodecMapping that = (MetadataCodecMapping) o;
        return Objects.equals(types, that.types);
    }

    @Override
    public int hashCode() {
        return Objects.hash(types);
    }
}
