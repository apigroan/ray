package com.blindskipper.ray.jvm.attribute;


import com.blindskipper.ray.jvm.ClassFileReader;

public class StackMapTableAttribute extends AttributeInfo {

    {
        u2("number_of_entries");
    }

    @Override
    protected void readContent(ClassFileReader reader) {
        super.readContent(reader);
        reader.skipBytes(super.getUInt("attribute_length") - 2);
    }

}
