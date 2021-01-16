package com.blindskipper.ray.jvm;

import com.blindskipper.ray.common.FileAssembly;
import com.blindskipper.ray.jvm.constant.ConstantPool;
import com.blindskipper.ray.jvm.data.type.Bytes;
import com.blindskipper.ray.jvm.data.type.Table;
import com.blindskipper.ray.jvm.data.type.U1;
import com.blindskipper.ray.jvm.data.type.U1CpIndex;
import com.blindskipper.ray.jvm.data.type.U2;
import com.blindskipper.ray.jvm.data.type.U2AccessFlags;
import com.blindskipper.ray.jvm.data.type.U2CpIndex;
import com.blindskipper.ray.jvm.data.type.U4;
import com.blindskipper.ray.jvm.data.type.U4Hex;
import com.blindskipper.ray.jvm.data.type.UInt;

public abstract class ClassAssembly extends FileAssembly {

    public final void read(ClassFileReader reader) {
        try {
            int offset = reader.getPosition();
            readContent(reader);
            int length = reader.getPosition() - offset;
            super.setOffset(offset);
            super.setLength(length);
        } catch (Exception e) {
            throw e;
        }
    }

    protected void readContent(ClassFileReader reader) {
        for (FileAssembly fc : getParts()) {
            ((ClassAssembly) fc).read(reader);
        }
    }

    protected void postRead(ConstantPool cp) {

    }

    protected int getUInt(String name) {
        return ((UInt) get(name)).getValue();
    }

    protected final void u1(String name) {
        this.add(name, new U1());
    }

    protected final void u1cp(String name) {
        this.add(name, new U1CpIndex());
    }

    protected final void u2(String name) {
        this.add(name, new U2());
    }

    protected final void u2cp(String name) {
        this.add(name, new U2CpIndex());
    }

    protected final void u2af(String name, int afType) {
        this.add(name, new U2AccessFlags(afType));
    }

    protected final void u4(String name) {
        this.add(name, new U4());
    }

    protected final void u4hex(String name) {
        this.add(name, new U4Hex());
    }

    protected final void table(String name,
                               Class<? extends ClassAssembly> entryClass) {
        UInt length = (UInt) getParts().get(getParts().size() - 1);
        Table table = new Table(length, entryClass);
        this.add(name, table);
    }

    protected final void bytes(String name) {
        UInt count = (UInt) getParts().get(getParts().size() - 1);
        Bytes bytes = new Bytes(count);
        this.add(name, bytes);
    }

    protected final void add(ClassAssembly subPart) {
        this.add(null, subPart);
    }

}
