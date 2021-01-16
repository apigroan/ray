package com.blindskipper.ray.jvm;


import com.blindskipper.ray.jvm.attribute.AttributeInfo;
import com.blindskipper.ray.jvm.benua.AccessFlagType;
import com.blindskipper.ray.jvm.constant.ConstantPool;
import com.blindskipper.ray.jvm.data.type.U2;
import com.blindskipper.ray.jvm.data.type.U2CpIndex;

public class ClassFile extends ClassAssembly {

    {
        U2 cpCount = new U2();

        u4hex("magic");
        u2   ("minor_version");
        u2   ("major_version");
        add  ("constant_pool_count", cpCount);
        add  ("constant_pool", new ConstantPool(cpCount));
        u2af ("access_flags", AccessFlagType.AF_CLASS);
        u2cp ("this_class");
        u2cp ("super_class");
        u2   ("interfaces_count");
        table("interfaces", U2CpIndex.class);
        u2   ("fields_count");
        table("fields", FieldInfo.class);
        u2   ("methods_count");
        table("methods", MethodInfo.class);
        u2   ("attributes_count");
        table("attributes", AttributeInfo.class);
    }

    public ConstantPool getConstantPool() {
        return (ConstantPool) super.get("constant_pool");
    }

}
