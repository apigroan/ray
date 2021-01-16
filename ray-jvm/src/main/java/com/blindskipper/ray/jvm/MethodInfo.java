package com.blindskipper.ray.jvm;

import com.blindskipper.ray.jvm.attribute.AttributeInfo;
import com.blindskipper.ray.jvm.benua.AccessFlagType;
import com.blindskipper.ray.jvm.constant.ConstantPool;

public class MethodInfo extends ClassAssembly {

    {
        u2af ("access_flags", AccessFlagType.AF_METHOD);
        u2cp ("name_index");
        u2cp ("descriptor_index");
        u2   ("attributes_count");
        table("attributes", AttributeInfo.class);
    }

    @Override
    protected void postRead(ConstantPool cp) {
        int nameIndex = super.getUInt("name_index");
        int descIndex = super.getUInt("descriptor_index");
        if (nameIndex > 0) {
            setDescription(cp.getUtf8String(nameIndex) + cp.getUtf8String(descIndex));
        }
    }
    
}
