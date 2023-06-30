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
import de.florianmichael.betapackets.model.base.ProtocolCollection;

import java.util.function.BiConsumer;
import java.util.function.Function;

public enum MetadataTypes implements IMetadataWriter {
    // v1.8
    BYTE(new MetadataWriter1_8.ByteMetadataWriter(), 0, 0),
    SHORT(new MetadataWriter1_8.ShortMetadataWriter(), 1, -1),
    INT(new MetadataWriter1_8.IntMetadataWriter(), 2, -1),
    FLOAT(new MetadataWriter1_8.FloatMetadataWriter(), 3, 2),
    STRING(new MetadataWriter1_8.StringMetadataWriter(), 4, 3),
    SLOT(new MetadataWriter1_8.ItemStackMetadataWriter(), 5, 5),
    BLOCK_POS(new MetadataWriter1_8.BlockPosMetadataWriter(), 6, 8),
    ROTATIONS(new MetadataWriter1_8.RotationsMetadataWriter(), 7, 7),

    // v1.9
    VAR_INT(new MetadataWriter1_9.VarIntMetadataWriter(), -1, 1),
    CHAT(new MetadataWriter1_9.ChatMetadataWriter(), -1, 4),
    BOOLEAN(new MetadataWriter1_9.BooleanMetadataWriter(), -1, 6),
    OPTIONAL_BLOCK_POS(new MetadataWriter1_9.OptionalBlockPosMetadataWriter(), -1, 9),
    FACING(new MetadataWriter1_9.FacingMetadataWriter(), -1, 10),
    OPTIONAL_UUID(new MetadataWriter1_9.OptionalUUIDMetadataWriter(), -1, 11),
    BLOCK_ID(new MetadataWriter1_9.VarIntMetadataWriter(), -1, 12);

    public final IMetadataWriter rewriter;
    public final int v1_8Id;
    public final int v1_9Id;

    MetadataTypes(IMetadataWriter rewriter, int v1_8Id, int v1_9Id) {
        this.rewriter = rewriter;
        this.v1_8Id = v1_8Id;
        this.v1_9Id = v1_9Id;
    }

    @Override
    public Function<FunctionalByteBuf, Object> getReader() throws Exception {
        return rewriter.getReader();
    }

    @Override
    public BiConsumer<FunctionalByteBuf, Object> getWriter() throws Exception {
        return rewriter.getWriter();
    }

    public static MetadataTypes getById(final ProtocolCollection version, final int id) {
        for (MetadataTypes value : values()) {
            final int fieldId = value.getId(version);
            if (fieldId == -1) continue;
            if (fieldId == id) return value;
        }
        return null;
    }

    public int getId(final ProtocolCollection version) {
        if (version.isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            return v1_9Id;
        }
        return v1_8Id;
    }
}
