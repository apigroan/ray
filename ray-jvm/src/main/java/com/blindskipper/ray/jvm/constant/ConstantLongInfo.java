package com.blindskipper.ray.jvm.constant;

public class ConstantLongInfo extends ConstantInfo {

    {
        u4hex("high_bytes");
        u4hex("low_bytes");
    }

    @Override
    protected String loadDesc(ConstantPool cp) {
        long high = super.getUInt("high_bytes");
        long low = super.getUInt("low_bytes") & 0xffffffffL;
        long l = (high << 32) + low;
        return String.valueOf(l);
    }
    
}
