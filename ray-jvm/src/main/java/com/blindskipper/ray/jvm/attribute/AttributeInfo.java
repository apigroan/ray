package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.jvm.ClassAssembly;

public abstract class AttributeInfo extends ClassAssembly {

    {
        u2("attribute_name_index");
        u4("attribute_length");
    }

}
