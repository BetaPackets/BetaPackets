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

import java.util.Objects;

public class Metadata {

    private final int index;
    private final String name;
    private final MetadataCodecType codecType;

    public Metadata(int index, String name, MetadataCodecType codecType) {
        this.index = index;
        this.name = name;
        this.codecType = codecType;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public MetadataCodecType getCodecType() {
        return codecType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metadata metadata = (Metadata) o;
        return index == metadata.index && Objects.equals(name, metadata.name) && codecType == metadata.codecType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, name, codecType);
    }

    @Override
    public String toString() {
        return index + ":" + name + ":" + codecType.name().toLowerCase();
    }
}
