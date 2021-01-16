package com.blindskipper.ray.jvm.constant;

import com.blindskipper.ray.jvm.ClassAssembly;

public abstract class ConstantInfo extends ClassAssembly {

    {
        u1("tag");
    }

    protected abstract String loadDesc(ConstantPool cp);
    
}
