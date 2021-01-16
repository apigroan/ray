package com.blindskipper.ray.jvm.constant;

public class ConstantStringInfo extends ConstantInfo {

    {
        u2("string_index");
    }

    @Override
    protected String loadDesc(ConstantPool cp) {
        int stringIndex = super.getUInt("string_index");
        return cp.getUtf8Info(stringIndex).loadDesc(cp);
    }
    
}
