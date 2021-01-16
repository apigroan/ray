package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.jvm.data.type.U2CpIndex;

public class ModulePackagesAttribute extends AttributeInfo {

    {
        u2("package_count");
        table("package_index", U2CpIndex.class);
    }

}
