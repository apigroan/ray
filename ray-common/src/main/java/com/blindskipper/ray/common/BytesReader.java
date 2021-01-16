package com.blindskipper.ray.common;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BytesReader {
    
    private final ByteBuffer bb;

    public BytesReader(byte[] data, ByteOrder order) {
        this.bb = ByteBuffer.wrap(data)
                .asReadOnlyBuffer()
                .order(order);
    }

    public int getPosition() {
        return bb.position();
    }

    public byte getByte(int index) {
        return bb.get(index);
    }

    public short getShort(int index) {
        return bb.getShort(index);
    }

    public byte readByte() {
        return bb.get();
    }
    
    public int readUnsignedByte() {
        return Byte.toUnsignedInt(bb.get());
    }
    
    public short readShort() {
        return bb.getShort();
    }
    
    public int readUnsignedShort() {
        return Short.toUnsignedInt(bb.getShort());
    }
    
    public int readInt() {
        return bb.getInt();
    }

    public byte[] readBytes(int n) {
        byte[] bytes = new byte[n];
        bb.get(bytes);
        return bytes;
    }

    public void skipBytes(int n) {
        for (int i = 0; i < n; i++) {
            bb.get();
        }
    }

}
