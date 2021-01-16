package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.constant.ConstantPool;

public class LocalVariableTableAttribute extends AttributeInfo {

    {
        u2   ("local_variable_table_length");
        table("local_variable_table", LocalVariableTableEntry.class);
    }
    
    public static class LocalVariableTableEntry extends ClassAssembly {

        {
            u2  ("start_pc");
            u2  ("length");
            u2cp("name_index");
            u2cp("descriptor_index");
            u2  ("index");
        }

        @Override
        protected void postRead(ConstantPool cp) {
            int startPc = super.getUInt("start_pc");
            int length = super.getUInt("length");
            int nameIndex = super.getUInt("name_index");

            int fromPc = startPc;
            int toPc = fromPc + length - 1;
            String varName = cp.getConstantDesc(nameIndex);
            setDescription(String.format("%s(%d~%d)", varName, fromPc, toPc));
        }
        
    }
    
}
