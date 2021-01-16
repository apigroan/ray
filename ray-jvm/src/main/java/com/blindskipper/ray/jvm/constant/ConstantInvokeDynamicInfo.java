package com.blindskipper.ray.jvm.constant;

public class ConstantInvokeDynamicInfo extends ConstantInfo {

    {
        u2("bootstrap_method_attr_index");
        u2("name_and_type_index");
    }
    
    @Override
    protected String loadDesc(ConstantPool cp) {
        int nameAndTypeIndex = super.getUInt("name_and_type_index");
        return cp.getNameAndTypeInfo(nameAndTypeIndex).loadDesc(cp);
    }
    
}
