package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.benua.AccessFlagType;

public class InnerClassesAttribute extends AttributeInfo {

    {
        u2   ("number_of_classes");
        table("classes", InnerClassInfo.class);
    }
    
    public static class InnerClassInfo extends ClassAssembly {

        {
            u2cp("inner_class_info_index");
            u2cp("outer_class_info_index");
            u2cp("inner_name_index");
            u2af("inner_class_access_flags", AccessFlagType.AF_NESTED_CLASS);
        }
        
    }
    
}
