package com.blindskipper.ray.jvm.constant;

public class ConstantMethodTypeInfo extends ConstantInfo {

    {
        u2("descriptor_index");
    }
    
    @Override
    protected String loadDesc(ConstantPool cp) {
        int descriptorIndex = super.getUInt("descriptor_index");
        return cp.getUtf8String(descriptorIndex);
    }
    
}
