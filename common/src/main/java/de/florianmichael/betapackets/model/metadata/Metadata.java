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

package de.florianmichael.betapackets.model.metadata;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;

public class Metadata {

    public int index;
    public MetadataTypes metadataType;
    public Object value;

    public Metadata(int index, MetadataTypes metadataType, FunctionalByteBuf transformer) {
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
}
