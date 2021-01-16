package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.jvm.ClassAssembly;

public class RuntimeVisibleParameterAnnotationsAttribute extends AttributeInfo {

    {
        u1   ("num_parameters");
        table("parameter_annotations", ParameterAnnotationInfo.class);
    }
    
    
    public static class ParameterAnnotationInfo extends ClassAssembly {

        {
            u2   ("num_annotations");
            table("annotations", RuntimeVisibleAnnotationsAttribute.AnnotationInfo.class);
        }
        
    }
    
}
