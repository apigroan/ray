package com.blindskipper.ray.jvm.data.type;

import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.ClassFileReader;

public class Bytes extends ClassAssembly {

    private UInt count;

    public Bytes(UInt count) {
        this.count = count;
    }

    @Override
    protected void readContent(ClassFileReader reader) {
        reader.skipBytes(count.getValue());
    }

}
