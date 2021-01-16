package com.blindskipper.ray.jvm.constant;

public class ConstantIntegerInfo extends ConstantInfo {

    {
        u4("bytes");
    }

    @Override
    protected String loadDesc(ConstantPool cp) {
        int i = super.getUInt("bytes");
        return String.valueOf(i);
    }
    
}
