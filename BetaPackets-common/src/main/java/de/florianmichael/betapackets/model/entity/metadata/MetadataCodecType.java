package de.florianmichael.betapackets.model.entity.metadata;

import de.florianmichael.betapackets.base.bytebuf.FunctionalByteBuf;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.base.Reader;
import de.florianmichael.betapackets.model.base.Writer;
import de.florianmichael.betapackets.model.entity.EntityPose;
import de.florianmichael.betapackets.model.position.Facing;
import de.florianmichael.betapackets.model.position.Rotations;


public enum MetadataCodecType {

    BYTE(FunctionalByteBuf::writeByte, FunctionalByteBuf::readByte),
    INT((buf, object) -> {
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            buf.writeVarInt(object);
        } else {
            buf.writeInt(object);
        }
    }, (buf) -> {
        if (buf.getProtocolVersion().isNewerThanOrEqualTo(ProtocolCollection.R1_9)) {
            return buf.readVarInt();
        } else {
            return buf.readInt();
        }
    }),
    LONG(FunctionalByteBuf::writeVarLong, FunctionalByteBuf::readVarLong),
    SHORT(FunctionalByteBuf::writeShort, FunctionalByteBuf::readShort),
    FLOAT(FunctionalByteBuf::writeFloat, FunctionalByteBuf::readFloat),
    STRING(FunctionalByteBuf::writeString, FunctionalByteBuf::readString),
    COMPONENT(FunctionalByteBuf::writeComponent, FunctionalByteBuf::readComponent),
    OPTIONAL_COMPONENT(FunctionalByteBuf::writeOptionalComponent, FunctionalByteBuf::readOptionalComponent),
    ITEM_STACK(FunctionalByteBuf::writeItemStack, FunctionalByteBuf::readItemStack),
    BOOLEAN(FunctionalByteBuf::writeBoolean, FunctionalByteBuf::readBoolean),
    ROTATIONS((buf, object) -> {
        buf.writeFloat(object.pitch);
        buf.writeFloat(object.yaw);
        buf.writeFloat(object.roll);
    }, buf -> new Rotations(buf.readFloat(), buf.readFloat(), buf.readFloat())),
    BLOCK_POS(FunctionalByteBuf::writeBlockPos, FunctionalByteBuf::readBlockPos),
    OPTIONAL_BLOCK_POS(FunctionalByteBuf::writeOptionalBlockPos, FunctionalByteBuf::readOptionalBlockPos),
    DIRECTION(FunctionalByteBuf::writeEnumConstant, buf -> buf.readEnumConstant(Facing.class)),
    OPTIONAL_UUID(FunctionalByteBuf::writeOptionalUUID, FunctionalByteBuf::readOptionalUUID),
    BLOCK_STATE((buf, object) -> {
        buf.writeVarInt(object.getId());
    }, buf -> buf.getProtocolVersion().getBlockMapping().getStateById(buf.readVarInt())),
    OPTIONAL_BLOCK_STATE((buf, object) -> {
        buf.writeOptional(object, (buf1, val) -> buf1.writeVarInt(val.getId()));
    }, buf -> buf.readOptional(buf1 -> buf1.getProtocolVersion().getBlockMapping().getStateById(buf1.readVarInt()))),
    COMPOUND_TAG(FunctionalByteBuf::writeCompoundTag, FunctionalByteBuf::readCompoundTag),
    PARTICLE(null, null), /* MEGA TODO!!! SEHR WICHTIG DIE PARAMETER CODECS SCHREIBEN*/
    VILLAGER_DATA(null, null), /* MEGA TODO!!!2 VILLAGER TYPE UND PROFESSION MAPPEN*/
    OPTIONAL_UNSIGNED_INT((buf, object) -> {
        buf.writeOptional(object, FunctionalByteBuf::writeVarInt);
    }, buf -> buf.readOptional(FunctionalByteBuf::readVarInt)),
    POSE(FunctionalByteBuf::writeEnumConstant, buf -> buf.readEnumConstant(EntityPose.class)),
    CAT_VARIANT(null, null), /* TODO */
    FROG_VARIANT(null, null), /* TODO */
    OPTIONAL_GLOBAL_POS(null, null), /* TODO */
    PAINTING_VARIANT(null, null), /* TODO */
    SNIFFER_STATE(null, null), /* TODO */
    VECTOR3(FunctionalByteBuf::writeVec3f, FunctionalByteBuf::readVec3f),
    QUATERNION(FunctionalByteBuf::writeQuaternionf, FunctionalByteBuf::readQuaternionf);

    private Writer<?> writer;
    private Reader<?> reader;

    <T> MetadataCodecType(Writer<T> writer, Reader<T> reader) {
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
