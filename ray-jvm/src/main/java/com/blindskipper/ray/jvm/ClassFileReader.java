package com.blindskipper.ray.jvm;


import com.blindskipper.ray.common.BytesReader;
import com.blindskipper.ray.jvm.constant.ConstantPool;

import java.nio.ByteOrder;

public class ClassFileReader extends BytesReader {

    private ConstantPool constantPool;

    public ClassFileReader(byte[] data) {
        super(data, ByteOrder.BIG_ENDIAN);
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

}
