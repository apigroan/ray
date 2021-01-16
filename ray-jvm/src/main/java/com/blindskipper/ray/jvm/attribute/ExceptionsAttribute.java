package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.jvm.data.type.U2CpIndex;

public class ExceptionsAttribute extends AttributeInfo {

    {
        u2   ("number_of_exceptions");
        table("exception_index_table", U2CpIndex.class);
    }
    
}
