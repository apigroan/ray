package com.blindskipper.ray.jvm.bytecode;

import com.blindskipper.ray.jvm.ClassFileReader;
import com.blindskipper.ray.jvm.benua.Opcode;

public class Wide extends Instruction {

    public Wide(Opcode opcode, int pc) {
        super(opcode, pc);
    }
    
    @Override
    protected void readOperands(ClassFileReader reader) {
        int wideOpcode = reader.readUnsignedByte();
        if (wideOpcode == Opcode.iinc.opcode) {
            reader.skipBytes(4);
        } else {
            reader.skipBytes(2);
        }
    }
    
}
