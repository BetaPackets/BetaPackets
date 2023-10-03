package de.florianmichael.betapackets.model.particle;

import de.florianmichael.betapackets.netty.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.base.Reader;
import de.florianmichael.betapackets.model.base.Writer;

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
