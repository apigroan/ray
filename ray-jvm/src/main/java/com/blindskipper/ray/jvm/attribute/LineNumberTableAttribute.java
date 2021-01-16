package com.blindskipper.ray.jvm.attribute;

import com.blindskipper.ray.jvm.ClassAssembly;
import com.blindskipper.ray.jvm.constant.ConstantPool;

public class LineNumberTableAttribute extends AttributeInfo {

    {
        u2   ("line_number_table_length");
        table("line_number_table", LineNumberTableEntry.class);
    }
    
    public static class LineNumberTableEntry extends ClassAssembly {

        {
            u2("start_pc");
            u2("line_number");
        }

        @Override
        protected void postRead(ConstantPool cp) {
            int lineNumber = super.getUInt("line_number");
            int startPc = super.getUInt("start_pc");
            setName("line " + lineNumber);
            setDescription(Integer.toString(startPc));
        }

    }
    
}
