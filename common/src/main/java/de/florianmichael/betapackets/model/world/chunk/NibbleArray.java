package de.florianmichael.betapackets.model.world.chunk;

import java.util.Arrays;

public class NibbleArray {

    public byte[] data = new byte[2048];

    public NibbleArray() {
    }

    public NibbleArray(byte[] storageArray) {
        this.data = storageArray;
    }

    public int get(int x, int y, int z) {
        return this.getFromIndex(this.getCoordinateIndex(x, y, z));
    }

    public void set(int x, int y, int z, int value) {
        this.setIndex(this.getCoordinateIndex(x, y, z), value);
    }

    private int getCoordinateIndex(int x, int y, int z) {
        return y << 8 | z << 4 | x;
    }

    public int getFromIndex(int index) {
        int i = this.getNibbleIndex(index);
        return this.isLowerNibble(index) ? this.data[i] & 15 : this.data[i] >> 4 & 15;
    }

    public void setIndex(int index, int value) {
        int i = this.getNibbleIndex(index);

        if (this.isLowerNibble(index)) {
            this.data[i] = (byte) (this.data[i] & 240 | value & 15);
        } else {
            this.data[i] = (byte) (this.data[i] & 15 | (value & 15) << 4);
        }
    }

    private boolean isLowerNibble(int index) {
        return (index & 1) == 0;
    }

    private int getNibbleIndex(int index) {
        return index >> 1;
    }

    @Override
    public String toString() {
        return "NibbleArray{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NibbleArray that = (NibbleArray) o;

        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
}
