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

package de.florianmichael.betapackets.base.bytebuf;

import de.florianmichael.betapackets.api.UserConnection;
import de.florianmichael.betapackets.model.base.ProtocolCollection;
import de.florianmichael.betapackets.model.item.ItemStackV1_3;
import de.florianmichael.betapackets.model.metadata.Metadata;
import de.florianmichael.betapackets.model.metadata.MetadataTypes;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.lenni0451.mcstructs.nbt.io.NbtIO;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.text.ATextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FunctionalByteBuf extends PrimitiveByteBuf {

    private final UserConnection userConnection;

    public FunctionalByteBuf(ByteBuf buffer, final UserConnection userConnection) {
        super(buffer);
        this.userConnection = userConnection;
    }

    public ItemStackV1_3 readItemStack() {
        try {
            int id = this.readShort();

            if (id >= 0) {
                final int count = this.readByte();
                final int damage = this.readShort();

                return new ItemStackV1_3(id, count, damage, this.readCompoundTag());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeItemStack(ItemStackV1_3 stack) {
        try {
            if (stack == null) {
                this.writeShort(-1);
            } else {
                this.writeShort(stack.itemId);
                this.writeByte(stack.count);
                this.writeShort(stack.damage);
                this.writeCompoundTag(stack.tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CompoundTag readCompoundTag() {
        try {
            final DataInput dataInput = new DataInputStream(new ByteBufInputStream(getBuffer()));
            return NbtIO.JAVA.getReader().readCompound(dataInput, NbtReadTracker.unlimited());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeCompoundTag(final CompoundTag tag) {
        try {
            final DataOutput dataOutput = new DataOutputStream(new ByteBufOutputStream(getBuffer()));
            NbtIO.JAVA.getWriter().write(dataOutput, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Metadata> readMetadata() {
        final List<Metadata> list = new ArrayList<>();
        for (int i = readByte(); i != Byte.MAX_VALUE; i = readByte()) {
            list.add(new Metadata(i & 31, MetadataTypes.getById(getProtocolVersion(), (i & 224) >> 5), this));
        }
        return list;
    }

    public void writeMetadata(final List<Metadata> metadata) {
        for (Metadata metadatum : metadata) {
            this.writeByte((metadatum.metadataType.getId(getProtocolVersion()) << 5 | metadatum.index & 31));
            metadatum.metadataType.getWriter().accept(this, metadatum);
        }
    }

    public ATextComponent readComponent() {
        final String text = readString(32767);
        if (getProtocolVersion() == ProtocolCollection.R1_8) {
            return TextComponentSerializer.V1_8.deserialize(text);
        } else if (getProtocolVersion().isNewerThan(ProtocolCollection.R1_9)) {
            return TextComponentSerializer.V1_9.deserialize(text);
        }
        return null;
    }

    public void writeComponent(final ATextComponent component) {
        if (getProtocolVersion() == ProtocolCollection.R1_8) {
            writeString(TextComponentSerializer.V1_8.serialize(component));
        } else if (getProtocolVersion().isNewerThan(ProtocolCollection.R1_9)) {
            writeString(TextComponentSerializer.V1_9.serialize(component));
        }
    }

    public UserConnection getUserConnection() {
        return userConnection;
    }

    public ProtocolCollection getProtocolVersion() {
        return userConnection.getProtocolVersion();
    }
}
