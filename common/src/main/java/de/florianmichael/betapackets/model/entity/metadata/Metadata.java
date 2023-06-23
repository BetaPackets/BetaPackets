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

package de.florianmichael.betapackets.model.entity.metadata;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

import java.util.Objects;

public class Metadata {

    public int index;
    public MetadataTypes metadataType;
    public Object value;

    public Metadata(int index, MetadataTypes metadataType, FunctionalByteBuf transformer) throws Exception {
        this.index = index;
        this.metadataType = metadataType;
        this.value = metadataType.getReader().apply(transformer);
    }

    @Override
    public String toString() {
        return "Metadata{" +
                "index=" + index +
                ", metadataType=" + metadataType +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metadata metadata = (Metadata) o;

        if (index != metadata.index) return false;
        if (metadataType != metadata.metadataType) return false;
        return Objects.equals(value, metadata.value);
    }

    @Override
    public int hashCode() {
        int result = index;
        result = 31 * result + (metadataType != null ? metadataType.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
