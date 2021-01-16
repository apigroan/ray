package com.blindskipper.ray.jvm.constant;

public class ConstantNameAndTypeInfo extends ConstantInfo {

    {
        u2("name_index");
        u2("descriptor_index");
    }

    public int getNameIndex() {
        return super.getUInt("name_index");
    }

    @Override
    protected String loadDesc(ConstantPool cp) {
        String name = cp.getUtf8String(super.getUInt("name_index"));
        String type = cp.getUtf8String(super.getUInt("descriptor_index"));
        return name + "&" + type;
    }
    
}
