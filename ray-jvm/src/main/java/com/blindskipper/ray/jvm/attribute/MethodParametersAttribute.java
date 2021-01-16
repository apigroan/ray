package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.jvm.ClassAssembly;

public class MethodParametersAttribute extends AttributeInfo {

    {
        u1   ("parameters_count");
        table("parameters", ParameterInfo.class);
    }
    
    public static class ParameterInfo extends ClassAssembly {

        {
            u2("name_index");
            u2("access_flags");
        }
        
    }
    
}
