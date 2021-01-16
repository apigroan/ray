package com.blindskipper.ray.jvm;


import com.blindskipper.ray.jvm.attribute.AttributeInfo;
import com.blindskipper.ray.jvm.benua.AccessFlagType;
import com.blindskipper.ray.jvm.constant.ConstantPool;

public class FieldInfo extends ClassAssembly {

    {
        u2af ("access_flags", AccessFlagType.AF_FIELD);
        u2cp ("name_index");
        u2cp ("descriptor_index");
        u2   ("attributes_count");
        table("attributes", AttributeInfo.class);
    }

    @Override
    protected void postRead(ConstantPool cp) {
        int nameIndex = super.getUInt("name_index");
        if (nameIndex > 0) {
            setDescription(cp.getUtf8String(nameIndex));
        }
    }
    
}
