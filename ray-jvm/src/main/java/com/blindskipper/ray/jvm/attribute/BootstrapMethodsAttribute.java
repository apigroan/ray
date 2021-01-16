package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.data.type.U2CpIndex;

public class BootstrapMethodsAttribute extends AttributeInfo {

    {
        u2   ("num_bootstrap_methods");
        table("bootstrap_methods", BootstrapMethodInfo.class);
    }

    
    public static class BootstrapMethodInfo extends ClassAssembly {

        {
            u2cp ("bootstrap_method_ref");
            u2   ("num_bootstrap_arguments");
            table("bootstrap_arguments", U2CpIndex.class);
        }
        
    }
    
}
