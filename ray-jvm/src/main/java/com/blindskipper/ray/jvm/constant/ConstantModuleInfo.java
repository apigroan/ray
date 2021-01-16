package com.blindskipper.ray.jvm.constant;

public class ConstantModuleInfo extends ConstantInfo {

    {
        u2("name_index");
    }

    public int getNameIndex() {
        return super.getUInt("name_index");
    }

    @Override
    protected String loadDesc(ConstantPool cp) {
        return cp.getUtf8String(getNameIndex());
    }
    
}
