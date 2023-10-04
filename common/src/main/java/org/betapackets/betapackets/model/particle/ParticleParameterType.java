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

package org.betapackets.betapackets.model.particle;

import org.betapackets.betapackets.netty.base.FunctionalByteBuf;
import org.betapackets.betapackets.model.base.Reader;
import org.betapackets.betapackets.model.base.Writer;

public enum ParticleParameterType {

    VAR_INT(FunctionalByteBuf::writeVarInt, FunctionalByteBuf::readVarInt),
    ITEM_STACK(FunctionalByteBuf::writeItemStack, FunctionalByteBuf::readItemStack),
    FLOAT(FunctionalByteBuf::writeFloat, FunctionalByteBuf::readFloat),
    POSITION_SOURCE(null, null) /* TODO */
    ;

    private final Writer<?> writer;
    private final Reader<?> reader;

    <T> ParticleParameterType(Writer<T> writer, Reader<T> reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public Writer<?> getWriter() {
        return writer;
    }

    public Reader<?> getReader() {
        return reader;
    }
}
