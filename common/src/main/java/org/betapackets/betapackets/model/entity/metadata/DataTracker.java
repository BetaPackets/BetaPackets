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

package org.betapackets.betapackets.model.entity.metadata;

import java.util.*;

public class DataTracker {

    private final Map<String, Metadata> typeToData = new HashMap<>();
    private final Map<Integer, Metadata> indexToData = new HashMap<>();

    public void register(Metadata data) {
        if (typeToData.containsKey(data.getName())) throw new IllegalArgumentException("Duplicate entry (name): " + typeToData.get(data.getName()) + " -> " + data);
        typeToData.put(data.getName(), data);

        if (indexToData.containsKey(data.getIndex())) throw new IllegalArgumentException("Duplicate entry (index): " + indexToData.get(data.getIndex()) + " -> " + data);
        indexToData.put(data.getIndex(), data);
    }

    public Collection<Metadata> getMetadata() {
        return typeToData.values();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataTracker that = (DataTracker) o;
        return Objects.equals(typeToData, that.typeToData) && Objects.equals(indexToData, that.indexToData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeToData, indexToData);
    }

    @Override
    public String toString() {
        return "DataTracker{" +
                "typeToData=" + typeToData +
                ", indexToData=" + indexToData +
                '}';
    }
}
