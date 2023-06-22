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

package de.florianmichael.betapackets.base;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.base.ProtocolCollection;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ModelMapper<T, K> {

    public T value;
    public K mappedValue;

    public final Function<FunctionalByteBuf, T> readFunction;
    public final BiConsumer<FunctionalByteBuf, T> writeFunction;
    public final BiFunction<ProtocolCollection, T, K> mapper;

    public ModelMapper(final BiConsumer<FunctionalByteBuf, T> writeFunction, final K mappedValue) {
        this.readFunction = null;
        this.writeFunction = writeFunction;
        this.mapper = null;

        this.mappedValue = mappedValue;
    }

    public ModelMapper(Function<FunctionalByteBuf, T> readFunction, BiConsumer<FunctionalByteBuf, T> writeFunction, BiFunction<ProtocolCollection, T, K> mapper) {
        this.readFunction = readFunction;
        this.writeFunction = writeFunction;
        this.mapper = mapper;
    }

    public void read(final FunctionalByteBuf buf) {
        if (readFunction == null || mapper == null) return;

        this.value = readFunction.apply(buf);
        this.mappedValue = mapper.apply(buf.getProtocolVersion(), this.value);
    }

    public void write(final FunctionalByteBuf buf) {
        writeFunction.accept(buf, value);
    }

    public K getValue() {
        return mappedValue;
    }

    @Override
    public String toString() {
        return "ModelMapper{" +
                "value=" + value +
                ", mappedValue=" + mappedValue +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelMapper<?, ?> that = (ModelMapper<?, ?>) o;

        if (!value.equals(that.value)) return false;
        return mappedValue.equals(that.mappedValue);
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + mappedValue.hashCode();
        return result;
    }
}
