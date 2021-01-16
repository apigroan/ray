package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.constant.ConstantPool;

public class LocalVariableTypeTableAttribute extends AttributeInfo {

    {
        u2   ("local_variable_type_table_length");
        table("local_variable_type_table", LocalVariableTypeTableEntry.class);
    }

    public static class LocalVariableTypeTableEntry extends ClassAssembly {

        {
            u2  ("start_pc");
            u2  ("length");
            u2cp("name_index");
            u2cp("signature_index");
            u2  ("index");
        }

        @Override
        protected void postRead(ConstantPool cp) {
            int nameIndex = super.getUInt("name_index");
            setDescription(cp.getUtf8String(nameIndex));
        }
    
    }
    
}
