package de.florianmichael.betapackets.model.item;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class ItemMapping {

    private Map<ItemType, Item> typeToItem = new HashMap<>();
    private Map<Integer, Item> idToItem = new HashMap<>();

    public void addItem(Item item) {
        typeToItem.put(item.getType(), item);
        idToItem.put(item.getId(), item);
    }

    public void write(OutputStream out) throws IOException {
        DataOutputStream data = new DataOutputStream(out);
        data.writeShort(typeToItem.size());
        for (Item value : typeToItem.values()) {

            int idMask = (value.getId() & 0b00000000_00000000_00000111_11111111) << 11;
            idMask |= value.getType().ordinal() & 0b00000000_00000000_00000111_11111111;
            data.writeByte(idMask & 0b00000000_00000000_00000000_11111111);
            data.writeByte((idMask >> 8) & 0b00000000_00000000_00000000_11111111);
            data.writeByte((idMask >> 16) & 0b00000000_00000000_00000000_11111111);

            int stackSize = value.getMaxStackSize();
            if (stackSize == 64) stackSize = 0;
            byte mask = (byte) ((stackSize & 0b00111111) << 2);
            mask |= value.getBlockType() == null ? 0x02 : 0;
            mask |= value.isEdible() ? 0x01 : 0;
            data.writeByte(mask);
        }
    }

}
