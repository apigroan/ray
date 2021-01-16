package com.blindskipper.ray.jvm.constant;

public class ConstantFloatInfo extends ConstantInfo {

    {
        u4("bytes");
    }
    
    @Override
    protected String loadDesc(ConstantPool cp) {
        float f = Float.intBitsToFloat(super.getUInt("bytes"));
        return Float.toString(f);
    }
    
}
